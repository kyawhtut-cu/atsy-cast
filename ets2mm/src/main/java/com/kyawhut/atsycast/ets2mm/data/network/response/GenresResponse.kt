package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class GenresResponse(
    @SerializedName("genre_id")
    val genreID: String,
    @SerializedName("name")
    val genreName: String,
) : Serializable
