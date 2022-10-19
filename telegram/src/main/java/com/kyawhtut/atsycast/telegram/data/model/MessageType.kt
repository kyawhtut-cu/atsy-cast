package com.kyawhtut.atsycast.telegram.data.model

/**
 * Created by ios_dev - 19/10/22
 */
internal sealed class MessageType(val messageID: Long) {
    data class MessageTextModel(
        val id: Long,
        val text: String?
    ) : MessageType(id)

    data class MessagePhotoModel(
        val id: Long,
        val path: String,
        val caption: String?
    ) : MessageType(id)
}
