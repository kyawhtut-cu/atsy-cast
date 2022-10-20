package com.kyawhtut.atsycast.telegram.ui.chatdetail

import com.kyawhtut.atsycast.telegram.data.model.ChatDetailModel
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatByID
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatHistory
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getFile
import com.kyawhtut.atsycast.telegram.utils.error
import com.kyawhtut.atsycast.telegram.utils.map
import com.kyawhtut.atsycast.telegram.utils.success
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class ChatDetailRepoImpl @Inject constructor(
    private val telegram: Telegram
) : ChatDetailRepo {

    override suspend fun getChatDetail(chatID: Long): Response<ChatDetailModel> {
        return telegram.getChatByID(chatID).map { chat ->
            var chatPhoto = chat.photo?.big?.local?.path ?: ""
            if (chatPhoto.isEmpty()) {
                telegram.getFile(chat.photo?.big?.id).success {
                    chatPhoto = it.local.path
                }
            }
            ChatDetailModel(
                ChatModel(
                    chat.id,
                    chat.title,
                    chatPhoto,
                    chat.lastMessage?.id
                ),
                chat.lastMessage.toMessageType(chat.lastMessage.getPhoto())
            )
        }
    }

    override suspend fun getChatHistory(
        chatID: Long,
        lastMessageID: Long,
        limit: Int
    ): Response<List<MessageType>> {
        return telegram.getChatHistory(chatID, lastMessageID).map {
            it.messages.mapNotNull { message ->
                message.getPhoto() to message
            }.mapNotNull { (photoFile, message) ->
                message.toMessageType(photoFile)
            }
        }
    }

    private suspend fun TdApi.Message?.getPhoto(): TdApi.File? {
        if (this == null) return null
        return when (content?.constructor) {
            TdApi.MessageVideo.CONSTRUCTOR -> with(content as TdApi.MessageVideo) {
                if (video.thumbnail?.file?.local?.path?.isEmpty() == true) {
                    var file: TdApi.File? = null
                    telegram.getFile(video.thumbnail?.file?.id).success {
                        file = it
                    }.error {
                        it.printStackTrace()
                        file = null
                    }
                    file
                } else video.thumbnail?.file
            }

            TdApi.MessagePhoto.CONSTRUCTOR -> with(content as TdApi.MessagePhoto) {
                if (photo?.sizes?.isEmpty() == true) null
                else photo?.sizes?.first()?.let {
                    if (it.photo.local.path.isEmpty()) {
                        var file: TdApi.File? = null
                        telegram.getFile(it.photo.id).success { f ->
                            file = f
                        }.error { e ->
                            e.printStackTrace()
                            file = null
                        }
                        file
                    } else it.photo
                }
            }

            else -> null
        }
    }

    private fun TdApi.Message?.toMessageType(photo: TdApi.File?): MessageType? {
        if (this == null) return null
        return when (content.constructor) {
            TdApi.MessageVideo.CONSTRUCTOR -> with(content as TdApi.MessageVideo) {
                MessageType.MessageVideoModel(
                    id,
                    photo?.local?.path ?: "",
                    caption.text,
                    video.video.id,
                    video.video.size,
                    video.fileName,
                    video.duration
                )
            }

            TdApi.MessagePhoto.CONSTRUCTOR -> with(content as TdApi.MessagePhoto) {
                MessageType.MessagePhotoModel(
                    id,
                    photo?.local?.path ?: "",
                    caption.text
                )
            }

            else -> null
        }
    }
}
