package com.kyawhtut.atsycast.telegram.data.model

import androidx.leanback.widget.DiffCallback
import java.io.Serializable

/**
 * Created by ios_dev - 19/10/22
 */
internal sealed class MessageType(val messageID: Long) : Serializable {

    companion object {
        val diffUtil = object : DiffCallback<MessageType>() {
            override fun areItemsTheSame(oldItem: MessageType, newItem: MessageType): Boolean {
                return oldItem.messageID == newItem.messageID
            }

            override fun areContentsTheSame(oldItem: MessageType, newItem: MessageType): Boolean {
                return oldItem == newItem
            }
        }
    }

    data class MessageTextModel(
        val id: Long,
        val text: String?
    ) : MessageType(id)

    data class MessagePhotoModel(
        val id: Long,
        val path: String,
        val caption: String?
    ) : MessageType(id)

    data class MessageVideoModel(
        val id: Long,
        val thumbnailPath: String,
        val caption: String?,
        val videoID: Int,
        val videoSize: Int,
        val videoFileName: String,
        val videoDuration: Int
    ) : MessageType(id)
}
