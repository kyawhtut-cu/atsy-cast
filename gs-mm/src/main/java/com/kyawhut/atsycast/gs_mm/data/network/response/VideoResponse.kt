package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import com.kyawhut.atsycast.share.model.SubTitleModel
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
data class VideoResponse(
    @SerializedName("video_id")
    val videoID: String,
    @SerializedName("playlist_id")
    val playlistID: String,
    @SerializedName("video_title")
    val videoTitle: String,
    @SerializedName("video_description")
    var videoDescription: String,
    @SerializedName("video_poster")
    val videoPoster: String,
    @SerializedName("video_cover")
    val videoCover: String,
    @SerializedName("video_url")
    val videoURL: String,
    @SerializedName("video_company_name")
    val videoCompanyName: String,
    @SerializedName("video_content_type")
    val videoContentType: String,
    @SerializedName("video_episode_total")
    val videoEpisodeTotal: Int,
    @SerializedName("video_subtitles")
    val videoSubTitle: List<SubTitleModel>
) : Serializable {
    companion object {
        val diff = object : DiffCallback<VideoResponse>() {
            override fun areItemsTheSame(oldItem: VideoResponse, newItem: VideoResponse): Boolean {
                return oldItem.videoID == newItem.videoID
            }

            override fun areContentsTheSame(
                oldItem: VideoResponse,
                newItem: VideoResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
