package com.kyawhtut.atsycast.telegram.ui.chat

import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatByID
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getChatList
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getFile
import com.kyawhtut.atsycast.telegram.utils.map
import com.kyawhtut.atsycast.telegram.utils.success
import org.drinkless.td.libcore.telegram.TdApi
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
            }.map { chat ->
                var chatPhoto = chat.photo?.big?.local?.path ?: ""
                if (chatPhoto.isEmpty()) {
                    telegram.getFile(chat.photo?.big?.id).success {
                        chatPhoto = it.local.path
                    }
                }
                ChatModel(
                    chat.id,
                    chat.title,
                    chatPhoto,
                    chat.lastMessage?.id
                )
            }
        }
    }
}
