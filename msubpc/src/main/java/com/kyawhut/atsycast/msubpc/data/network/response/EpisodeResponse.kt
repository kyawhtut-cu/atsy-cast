package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/2/21
 */
@Keep
internal data class EpisodeResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("serie_id")
    val seriesID: String,
    @SerializedName("episodename")
    var episodeName: String,
    @SerializedName("stream")
    var stream: String? = null,
    @SerializedName("filesize")
    val fileSize: String = "",
    @SerializedName("vstream")
    var vStream: String? = null,
    @SerializedName("vbackup")
    var vbackup: String? = null,
    @SerializedName("freemium")
    var freemium: String? = null
) : Serializable {
    companion object {
        val diff = object : DiffCallback<EpisodeResponse>() {
            override fun areItemsTheSame(
                oldItem: EpisodeResponse,
                newItem: EpisodeResponse
            ): Boolean {
                return oldItem.id == newItem.id
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
