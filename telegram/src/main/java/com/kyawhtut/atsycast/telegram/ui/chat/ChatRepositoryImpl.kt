package com.kyawhtut.atsycast.telegram.ui.chat

import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatByID
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatList
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getFile
import com.kyawhtut.atsycast.telegram.utils.map
import com.kyawhtut.atsycast.telegram.utils.success
import org.drinkless.td.libcore.telegram.TdApi
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ios_dev - 19/10/22
 */
internal class ChatRepositoryImpl @Inject constructor(
    private val telegram: Telegram
) : ChatRepository {

    override suspend fun getChatList(): Response<List<ChatModel>> {
        return telegram.getChatList().map {
            // get chat ids
            it.chatIds.map { id ->
                telegram.getChatByID(id)
            }.mapNotNull { response ->
                if (response is Response.Success) {
                    // get chat detail
                    response.data
                } else null
            }.filter { chat ->
                when (chat.type.constructor) {
                    TdApi.ChatTypeSecret.CONSTRUCTOR -> true
                    TdApi.ChatTypePrivate.CONSTRUCTOR -> true
                    TdApi.ChatTypeSupergroup.CONSTRUCTOR -> true
                    else -> true
                }
            }
        }.map {
            it.map { chat ->
                // fetch chat photo
                telegram.getFile(chat.photo?.big?.id) to chat
            }.map { (response, chat) ->
                // get chat photo
                if (response is Response.Success) {
                    response.data.local.path to chat
                } else "" to chat
            }.map { (chatPhoto, chat) ->
                val lastMessage = chat.lastMessage
                ChatModel(
                    chat.id,
                    chat.title,
                    chatPhoto,
                    when (lastMessage?.content?.constructor) {
                        TdApi.MessageText.CONSTRUCTOR -> with(lastMessage.content as TdApi.MessageText) {
                            MessageType.MessageTextModel(
                                lastMessage.id,
                                text.text
                            )
                        }

                        TdApi.MessagePhoto.CONSTRUCTOR -> with(lastMessage.content as TdApi.MessagePhoto) {
                            var photoPath = ""
                            telegram.getFile(
                                photo.sizes.first().photo.id
                            ).success { file ->
                                photoPath = file.local.path
                            }
                            MessageType.MessagePhotoModel(
                                lastMessage.id,
                                photoPath,
                                caption.text
                            )
                        }

                        TdApi.MessageVideo.CONSTRUCTOR -> with(lastMessage.content as TdApi.MessageVideo) {
                            var thumbnailPath = ""
                            telegram.getFile(
                                video.thumbnail?.file?.id
                            ).success {
                                thumbnailPath = it.local.path
                            }
                            MessageType.MessageVideoModel(
                                lastMessage.id,
                                thumbnailPath,
                                caption.text,
                                video.video.id,
                                video.video.size,
                                video.fileName,
                                video.duration,
                            )
                        }

                        else -> null
                    }
                ).also {
                    Timber.d("Message Chat Data => ${it.chatTitle} $it")
                }
            }
        }
    }
}
