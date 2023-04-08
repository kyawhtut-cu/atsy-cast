package com.kyawhut.astycast.gsmovie.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@Keep
data class VideoResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>?
) {

    @Keep
    data class Data(
        @SerializedName("video_id")
        val videoID: String,
        @SerializedName("video_title")
        val videoTitle: String,
        @SerializedName("video_poster")
        val videoPoster: String,
        @SerializedName("video_cover")
        val videoCover: String?,
        @SerializedName("video_view_count")
        val videoViewCount: String,
        @SerializedName("video_episode")
        val videoEpisode: String
    ) : Serializable {

        companion object {
            val diff = object : DiffCallback<Data>() {
                override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                    return oldItem.videoID == newItem.videoID
                }

                override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
