package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class SeasonResponse(
    @SerializedName("seasons_id")
    val seasonID: Int,
    @SerializedName("seasons_name")
    val seasonName: String,
    @SerializedName("episodes")
    val seasonEpisode: List<EpisodeResponse>
) : Serializable
