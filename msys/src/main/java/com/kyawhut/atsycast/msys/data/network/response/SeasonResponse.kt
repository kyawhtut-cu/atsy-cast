package com.kyawhut.atsycast.msys.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
internal data class SeasonResponse(
    @SerializedName("id")
    val seasonID: String,
    @SerializedName("title")
    val seasonTitle: String,
    @SerializedName("episodes")
    val seasonEpisodeList: List<EpisodeResponse>
) : Serializable {

    companion object {
        val diff = object : DiffCallback<SeasonResponse>() {
            override fun areItemsTheSame(
                oldItem: SeasonResponse,
                newItem: SeasonResponse
            ): Boolean {
                return oldItem.seasonID == newItem.seasonID
            }

            override fun areContentsTheSame(
                oldItem: SeasonResponse,
                newItem: SeasonResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
