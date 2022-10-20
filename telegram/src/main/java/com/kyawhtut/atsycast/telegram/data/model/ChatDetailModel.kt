package com.kyawhtut.atsycast.telegram.data.model

/**
 * Created by ios_dev - 20/10/22
 */
internal data class ChatDetailModel(
    val chatModel: ChatModel,
    val chatLastMessage: MessageType?
)
