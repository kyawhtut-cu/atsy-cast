package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
internal data class DrawerDetailResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>?
) {
    @Keep
    internal data class Data(
        @SerializedName("id")
        val id: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("video_list")
        val videoList: List<VideoResponse>
    )
}
