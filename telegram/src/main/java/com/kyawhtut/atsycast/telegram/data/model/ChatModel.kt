package com.kyawhtut.atsycast.telegram.data.model

/**
 * Created by ios_dev - 19/10/22
 */
internal data class ChatModel(
    val chatID: Long,
    val chatTitle: String,
    val chatPhoto: String,
    val chatLastMessage: MessageType?
)
