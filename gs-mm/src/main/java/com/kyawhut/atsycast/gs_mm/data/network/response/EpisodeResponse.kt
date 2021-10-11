package com.kyawhut.atsycast.gs_mm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import com.kyawhut.atsycast.share.model.SubTitleModel
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Keep
data class EpisodeResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Data>?
) {
    @Keep
    data class Data(
        @SerializedName("episode_id")
        val episodeID: String,
        @SerializedName("episode_title")
        val episodeTitle: String,
        @SerializedName("episode_description")
        val episodeDescription: String,
        @SerializedName("episode_cover")
        val episodeCover: String,
        @SerializedName("episode_url")
        val episodeURL: String,
        @SerializedName("episode_subtitle")
        val episodeSubtitle: List<SubTitleModel>
    ) : Serializable {

        companion object {
            val diff = object : DiffCallback<Data>() {
                override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                    return oldItem.episodeID == newItem.episodeID
                }

                override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
