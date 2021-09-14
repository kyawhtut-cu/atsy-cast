package com.kyawhut.atsycast.free2air.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Keep
data class Free2AirResponse(
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
    companion object {
        val diff = object : DiffCallback<Free2AirResponse>() {
            override fun areItemsTheSame(
                oldItem: Free2AirResponse,
                newItem: Free2AirResponse
            ): Boolean {
                return oldItem.channelID == newItem.channelID
            }

            override fun areContentsTheSame(
                oldItem: Free2AirResponse,
                newItem: Free2AirResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    var isPlaying: Boolean = false
}
