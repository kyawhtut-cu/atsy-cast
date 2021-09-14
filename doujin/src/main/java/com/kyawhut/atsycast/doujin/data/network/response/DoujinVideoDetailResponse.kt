package com.kyawhut.atsycast.doujin.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
data class DoujinVideoDetailResponse(
    @SerializedName("gid")
    val doujinID: String,
    @SerializedName("title")
    val doujinTitle: String,
    @SerializedName("slug")
    val doujinSLUG: String,
    @SerializedName("url")
    val doujinURL: String,
    @SerializedName("source")
    val doujinSource: String,
    @SerializedName("cover")
    val doujinImage: String,
    @SerializedName("length")
    val doujinLength: String
)
