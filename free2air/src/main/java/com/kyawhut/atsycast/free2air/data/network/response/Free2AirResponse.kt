package com.kyawhut.atsycast.free2air.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Keep
internal data class Free2AirResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: HashMap<String, List<Data>>
) {
    companion object {
        val diff = object : DiffCallback<Data>() {
            override fun areItemsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem.channelID == newItem.channelID
            }

            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    @Keep
    data class Data(
        @SerializedName("channel_id")
        val channelID: String,
        @SerializedName("channel_name")
        val channelName: String,
        @SerializedName("channel_order")
        val channelOrder: Int,
        @SerializedName("channel_status")
        val channelStatus: Boolean,
        @SerializedName("channel_cover")
        val channelCover: String,
        @SerializedName("channel_stream")
        val channelStream: String,
        @SerializedName("channel_origin")
        val channelOrigin: String,
        @SerializedName("channel_referer")
        val channelReferer: String,
        @SerializedName("channel_agent")
        val channelAgent: String
    ) {
        var isPlaying: Boolean = false
    }
}
