package com.kyawhut.atsycast.ets2mm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Keep
internal data class EpisodeResponse(
    @SerializedName("episodes_id")
    val episodeID: Int,
    @SerializedName("episodes_name")
    val episodeName: String,
    @SerializedName("stream_key")
    val episodeStreamKey: String,
    @SerializedName("file_type")
    val episodeFileType: String,
    @SerializedName("image_url")
    val episodeImage: String,
    @SerializedName("file_url")
    val episodeURL: String,
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
