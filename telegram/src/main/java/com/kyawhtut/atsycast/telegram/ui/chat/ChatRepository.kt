package com.kyawhtut.atsycast.telegram.ui.chat

import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.utils.Response

/**
 * Created by ios_dev - 19/10/22
 */
internal interface ChatRepository {

    suspend fun getChatList(): Response<List<ChatModel>>
}
