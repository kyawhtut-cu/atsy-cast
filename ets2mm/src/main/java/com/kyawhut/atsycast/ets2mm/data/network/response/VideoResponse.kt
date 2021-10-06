package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import com.kyawhut.atsycast.ets2mm.R
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
data class VideoResponse(
    @SerializedName("videos_id")
    val videoID: Int,
    @SerializedName("title")
    val videoTitle: String,
    @SerializedName("description")
    val videoDescription: String,
    @SerializedName("release")
    val videoYear: String,
    @SerializedName("video_quality")
    val videoQuality: String,
    @SerializedName("thumbnail_url")
    val videoPoster: String,
    @SerializedName("poster_url")
    val videoCover: String,
    @SerializedName("is_tvseries")
    val isTvSeries: Int? = 0,
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

    val isMovies: Boolean
        get() = isTvSeries == 0

    val qualityDrawable: Int
        get() = when {
            videoQuality.contains("Blu-Ray") -> R.drawable.bg_resolution_blu_ray
            videoQuality.contains("Web-dl") -> R.drawable.bg_resolution_web_dl
            videoQuality.contains("HD-Rip") -> R.drawable.bg_resolution_hd_rip
            else -> R.drawable.bg_resolution_default
        }

    val qualityColor: Int
        get() = when {
            videoQuality.contains("Blu-Ray") -> R.color.colorBluRay
            videoQuality.contains("Web-dl") -> R.color.colorWebDL
            videoQuality.contains("HD-Rip") -> R.color.colorHDRip
            else -> R.color.colorDefault
        }
}
