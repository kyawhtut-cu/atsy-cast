package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/2/21
 */
@Keep
internal data class SeriesResponse(
    @SerializedName("series") val series: List<VideoResponse>,
    @SerializedName("newepisodes") val newepisodes: List<VideoResponse>
)
