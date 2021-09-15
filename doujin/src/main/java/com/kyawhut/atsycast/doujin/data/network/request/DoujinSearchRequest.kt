package com.kyawhut.atsycast.doujin.data.network.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
data class DoujinSearchRequest(
    @SerializedName("q")
    val query: String,
    @SerializedName("page")
    val page: Int
)
