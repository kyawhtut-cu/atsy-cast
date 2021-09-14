package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@Keep
internal data class SearchResponse(
    @SerializedName("movies")
    val movies: List<VideoResponse>,
    @SerializedName("series")
    val series: List<VideoResponse>,
    @SerializedName("newepisodes")
    val newEpisodes: List<VideoResponse>,
)