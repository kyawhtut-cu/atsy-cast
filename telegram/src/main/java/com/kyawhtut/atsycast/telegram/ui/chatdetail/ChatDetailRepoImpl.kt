package com.kyawhtut.atsycast.telegram.ui.chatdetail

import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatHistory
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getFile
import com.kyawhtut.atsycast.telegram.utils.map
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal class ChatDetailRepoImpl @Inject constructor(
    private val telegram: Telegram
) : ChatDetailRepo {

    override suspend fun getChatHistory(
        chatID: Long,
        lastMessageID: Long,
        limit: Int
    ): Response<List<MessageType>> {
        return telegram.getChatHistory(chatID, lastMessageID).map {
            it.messages.mapNotNull { message ->
                when (message.content.constructor) {
                    TdApi.MessageVideo.CONSTRUCTOR -> telegram.getFile(
                        (message.content as TdApi.MessageVideo).video.thumbnail?.file?.id
                    )

                    TdApi.MessagePhoto.CONSTRUCTOR -> telegram.getFile(
                        (message.content as TdApi.MessagePhoto).photo?.sizes?.first()?.photo?.id
                    )

                    else -> null
                } to message
            }.map { (response, message) ->
                if (response is Response.Success) response.data.local.path to message
                else "" to message
            }.mapNotNull { (photoPath, message) ->
                when (message.content.constructor) {
                    TdApi.MessageVideo.CONSTRUCTOR -> with(message.content as TdApi.MessageVideo) {
                        MessageType.MessageVideoModel(
                            message.id,
                            photoPath,
                            caption.text,
                            video.video.id,
                            video.video.size,
                            video.fileName,
                            video.duration
                        )
                    }

                    TdApi.MessagePhoto.CONSTRUCTOR -> with(message.content as TdApi.MessagePhoto) {
                        MessageType.MessagePhotoModel(
                            message.id,
                            photoPath,
                            caption.text
                        )
                    }

                    else -> null
                }
            }
        }
    }
}
