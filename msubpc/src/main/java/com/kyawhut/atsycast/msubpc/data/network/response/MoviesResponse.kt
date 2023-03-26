package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 25/03/2023
 */
@Keep
data class MoviesResponse(
    @SerializedName("movies") val movies: List<VideoResponse>
)
