package com.kyawhut.atsycast.tiktok.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Keep
internal data class ThumbnailResponse(
    @SerializedName("default_thumbnail")
    val defaultThumbnail: String,
    @SerializedName("medium_thumbnail")
    val mediumThumbnail: String,
    @SerializedName("large_thumbnail")
    val largeThumbnail: String,
)
