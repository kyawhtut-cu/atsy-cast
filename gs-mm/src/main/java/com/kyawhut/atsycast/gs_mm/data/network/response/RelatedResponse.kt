package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/9/21
 */
@Keep
data class RelatedResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data?,
) {

    @Keep
    data class Data(
        @SerializedName("title")
        val title: String,
        @SerializedName("total")
        val total: Int,
        @SerializedName("playlist_list", alternate = ["related_list"])
        val videoList: List<VideoResponse>?
    )
}
