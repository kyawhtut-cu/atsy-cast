package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/11/21
 */
@Keep
internal data class SearchResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>
) {
    @Keep
    data class Data(
        @SerializedName("title")
        val title: String,
        @SerializedName("video_list")
        val videoList: List<VideoResponse>
    )
}
