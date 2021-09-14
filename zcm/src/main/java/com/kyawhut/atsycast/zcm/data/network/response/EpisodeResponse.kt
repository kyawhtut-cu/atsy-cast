package com.kyawhut.atsycast.zcm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
internal data class EpisodeResponse(
    @SerializedName("id")
    val episodeID: Int,
    @SerializedName("title")
    var episodeTitle: String,
    @SerializedName("description")
    val episodeDescription: String?,
    @SerializedName("image")
    val episodeImage: String?,
    @SerializedName("sources")
    var episodeSource: List<MoviesSourceResponse>
) : Serializable {
    companion object {
        val diff = object : DiffCallback<EpisodeResponse>() {
            override fun areItemsTheSame(
                oldItem: EpisodeResponse,
                newItem: EpisodeResponse
            ): Boolean {
                return oldItem.episodeID == newItem.episodeID
            }

            override fun areContentsTheSame(
                oldItem: EpisodeResponse,
                newItem: EpisodeResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
