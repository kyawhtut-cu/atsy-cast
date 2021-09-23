package com.kyawhut.atsycast.eporner.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/22/21
 */
@Keep
internal data class EPornerVideoResponse(
    @SerializedName("id")
    val epornerID: String,
    @SerializedName("title")
    val epornerTitle: String,
    @SerializedName("embed")
    val epornerURL: String,
    @SerializedName("default_thumb")
    val epornerThumbnail: EPornerThumbResponse
) : Serializable {
    companion object {
        val diff = object : DiffCallback<EPornerVideoResponse>() {
            override fun areItemsTheSame(
                oldItem: EPornerVideoResponse,
                newItem: EPornerVideoResponse
            ): Boolean {
                return oldItem.epornerID == newItem.epornerID
            }

            override fun areContentsTheSame(
                oldItem: EPornerVideoResponse,
                newItem: EPornerVideoResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
