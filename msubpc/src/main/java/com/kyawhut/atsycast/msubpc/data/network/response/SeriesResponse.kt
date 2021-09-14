package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/2/21
 */
@Keep
internal data class SeriesResponse(
    val id: String,
    @SerializedName("seriestitle")
    val seriesTitle: String,
    @SerializedName("seriesyear")
    val seriesYear: String,
    val image: String,
    val cover: String
)
