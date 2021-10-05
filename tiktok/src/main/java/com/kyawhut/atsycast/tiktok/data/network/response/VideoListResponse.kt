package com.kyawhut.atsycast.tiktok.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Keep
internal data class VideoListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val videoList: List<VideoResponse>
)
