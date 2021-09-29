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
internal data class VideoEpisodeResponse(
    @SerializedName("episode_id")
    val episodeID: Int,
    @SerializedName("episode_title")
    val episodeTitle: String,
    @SerializedName("episode_source")
    val episodeSource: List<VideoSourceResponse>
) : Serializable {
    companion object {
        val diff = object : DiffCallback<VideoEpisodeResponse>() {
            override fun areItemsTheSame(
                oldItem: VideoEpisodeResponse,
                newItem: VideoEpisodeResponse
            ): Boolean {
                return oldItem.episodeTitle == newItem.episodeTitle
            }

            override fun areContentsTheSame(
                oldItem: VideoEpisodeResponse,
                newItem: VideoEpisodeResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
