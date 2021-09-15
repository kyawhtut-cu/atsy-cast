package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class VideoDetailResponse(
    @SerializedName("videos_id")
    val videoID: Int,
    @SerializedName("title")
    val videoTitle: String,
    @SerializedName("description")
    val videoDescription: String,
    @SerializedName("runtime")
    val videoDuration: String,
    @SerializedName("video_quality")
    val videoQuality: String,
    @SerializedName("is_tvseries")
    val isTvSeries: Int,
    @SerializedName("thumbnail_url")
    val videoPoster: String,
    @SerializedName("poster_url")
    val videoCover: String,
    @SerializedName("genre")
    val videoGenres: List<GenresResponse>,
    @SerializedName("videos")
    val videoSource: List<MovieSourceResponse>?,
    @SerializedName("related_movie", alternate = ["related_tvseries"])
    val videoRelated: List<VideoResponse>?,
    @SerializedName("season")
    val videoSeason: List<SeasonResponse>?,
) : Serializable {
    val isMovies: Boolean
        get() = isTvSeries == 0
}
