package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 08/04/2023
 */
@Keep
data class AdultResponse(
    @SerializedName("id") val adultID: String,
    @SerializedName("movietitle") val adultTitle: String,
    @SerializedName("moviegenres") val adultGenres: String,
    @SerializedName("image") val adultImage: String,
    @SerializedName("movieyear") val adultYear: String,
    @SerializedName("stream") val adultStream: String?,
) {

    companion object {
        val diff = object : DiffCallback<AdultResponse>() {
            override fun areItemsTheSame(oldItem: AdultResponse, newItem: AdultResponse): Boolean {
                return oldItem.adultID == newItem.adultID
            }

            override fun areContentsTheSame(
                oldItem: AdultResponse,
                newItem: AdultResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
