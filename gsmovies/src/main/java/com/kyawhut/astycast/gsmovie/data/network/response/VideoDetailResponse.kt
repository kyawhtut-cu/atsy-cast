package com.kyawhut.astycast.gsmovie.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@Keep
internal data class VideoDetailResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data?
) {
    @Keep
    internal data class Data(
        @SerializedName("video_id")
        val videoID: String,
        @SerializedName("video_title")
        val videoTitle: String,
        @SerializedName("video_cover")
        val videoCover: String,
        @SerializedName("video_description")
        val videoDescription: String,
        @SerializedName("video_source")
        val videoSource: List<VideoSourceResponse>,
        @SerializedName("video_episode_source")
        val videoEpisodeSource: List<VideoEpisodeResponse>,
        @SerializedName("video_related")
        val videoRelated: List<VideoResponse.Data>
    ) : Serializable
}
