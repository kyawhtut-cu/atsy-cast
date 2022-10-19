package com.kyawhtut.atsycast.telegram.data.model

import androidx.leanback.widget.DiffCallback
import java.io.Serializable

/**
 * Created by ios_dev - 19/10/22
 */
internal data class ChatModel(
    val chatID: Long,
    val chatTitle: String,
    val chatPhoto: String,
    val chatLastMessage: MessageType?
): Serializable {

    companion object {
        val diffUtil = object : DiffCallback<ChatModel>() {
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem.chatID == newItem.chatID
            }

            override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
