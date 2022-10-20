package com.kyawhtut.atsycast.telegram.ui.chatdetail

import com.kyawhtut.atsycast.telegram.data.model.ChatDetailModel
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.utils.Response

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal interface ChatDetailRepo {

    suspend fun getChatDetail(chatID: Long): Response<ChatDetailModel>

    suspend fun getChatHistory(
        chatID: Long,
        lastMessageID: Long,
        limit: Int
    ): Response<List<MessageType>>
}
