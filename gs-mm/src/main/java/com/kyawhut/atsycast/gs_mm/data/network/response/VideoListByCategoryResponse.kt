package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
internal data class VideoListByCategoryResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<VideoResponse>?
)
