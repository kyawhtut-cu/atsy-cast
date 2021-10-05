package com.kyawhut.atsycast.tiktok.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Keep
internal data class VideoResponse(
    @SerializedName("video_id")
    val videoID: String,
    @SerializedName("video_description")
    val videoDescription: String,
    @SerializedName("video_cover")
    val videoCover: String,
    @SerializedName("video_dynamic_cover")
    val videoGIF: String,
    @SerializedName("video_play_url")
    val videoURL: String,
    @SerializedName("video_download_url")
    val videoDownloadURL: String,
    @SerializedName("video_author")
    val videoAuthor: AuthorResponse,
    @SerializedName("video_music")
    val videoMusic: MusicResponse,
)
