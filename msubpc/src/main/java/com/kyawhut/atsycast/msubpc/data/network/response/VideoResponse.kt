package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import com.kyawhut.atsycast.msubpc.R
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/2/21
 */
@Keep
internal data class VideoResponse(
    @SerializedName("id")
    var videoId: Int,
    @SerializedName("movietitle", alternate = ["seriestitle", "live_title"])
    var videoTitle: String = "",
    @SerializedName("movieyear", alternate = ["seriesyear"])
    val videoYear: String? = "",
    @SerializedName("image")
    val videoPosterImage: String,
    @SerializedName("cover")
    val videoCoverImage: String? = videoPosterImage,
    @SerializedName("moviegenres", alternate = ["seriesgenres", "language_name"])
    val videoGenres: String? = "",
    @SerializedName("review", alternate = [])
    val videoReview: String? = "",
    @SerializedName("language", alternate = [])
    val videoLanguage: String? = "",
    @SerializedName("imdb", alternate = [])
    val videoRating: String? = "",
    @SerializedName("quality", alternate = [])
    val videoQuality: String? = null,
    @SerializedName("seasonnumber", alternate = [])
    val videoSeasonNumber: Int = 0,
    @SerializedName("episodenumber", alternate = [])
    val videoEpisodeNumber: String? = "",
    @SerializedName("stream", alternate = [])
    val videoStream: String? = "",
    @SerializedName("streamsd", alternate = [])
    val videoStreamSD: String? = "",
    @SerializedName("vstream")
    val vStream: String? = ""
) : Serializable {
    companion object {
        val diff = object : DiffCallback<VideoResponse>() {
            override fun areItemsTheSame(oldItem: VideoResponse, newItem: VideoResponse): Boolean {
                return oldItem.videoId == newItem.videoId
            }

            override fun areContentsTheSame(
                oldItem: VideoResponse,
                newItem: VideoResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    var isMovies: Boolean = true

    val qualityDrawable: Int
        get() = when {
            (videoQuality ?: "").contains("Blu-Ray") -> R.drawable.bg_msub_resolution_blu_ray
            (videoQuality ?: "").contains("Web-dl") -> R.drawable.bg_msub_resolution_web_dl
            (videoQuality ?: "").contains("HD-Rip") -> R.drawable.bg_msub_resolution_hd_rip
            else -> R.drawable.bg_msub_resolution_default
        }

    val qualityColor: Int
        get() = when {
            (videoQuality ?: "").contains("Blu-Ray") -> R.color.colorBluRay
            (videoQuality ?: "").contains("Web-dl") -> R.color.colorWebDL
            (videoQuality ?: "").contains("HD-Rip") -> R.color.colorHDRip
            else -> R.color.colorDefault
        }
}
