package com.kyawhut.atsycast.zcm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/13/21
 */
@Keep
internal data class SearchResponse(
    @SerializedName("posters")
    val moviesList: List<MoviesResponse>
)
