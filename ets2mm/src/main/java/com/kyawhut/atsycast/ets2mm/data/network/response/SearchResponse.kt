package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class SearchResponse(
    @SerializedName("movie")
    val moviesList: List<VideoResponse>,
    @SerializedName("tvseries")
    val seriesList: List<VideoResponse>,
)
