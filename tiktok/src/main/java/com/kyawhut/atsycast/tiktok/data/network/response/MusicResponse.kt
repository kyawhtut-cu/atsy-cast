package com.kyawhut.atsycast.tiktok.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Keep
internal data class MusicResponse(
    @SerializedName("music_id")
    val musicID: String,
    @SerializedName("music_title")
    val musicTitle: String,
    @SerializedName("music_play_url")
    val musicURL: String,
    @SerializedName("music_author")
    val musicAuthorName: String,
    @SerializedName("music_thumbnail")
    val musicThumbnail: ThumbnailResponse
)
