package com.kyawhut.atsycast.doujin.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal data class DoujinPageVideoResponse(
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("videos")
    val data: List<DoujinVideoResponse>
)
