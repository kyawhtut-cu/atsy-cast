package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal data class FootballResponse(
    @SerializedName("football")
    val football: List<Data>,
) {

    @Keep
    internal data class Data(
        @SerializedName("id")
        val id: Int,
        @SerializedName("league")
        val league: String,
        @SerializedName("matchname")
        val matchName: String,
        @SerializedName("flag")
        val flag: String,
        @SerializedName("flag2")
        val flag2: String,
        @SerializedName("fhd")
        val streamFHD: String?,
        @SerializedName("fhd2")
        val streamFHD2: String?,
        @SerializedName("hd")
        val streamHD: String?,
        @SerializedName("hd2")
        val streamHD2: String?,
        @SerializedName("sd")
        val streamSD: String?,
        @SerializedName("sd2")
        val streamSD2: String?,
        @SerializedName("time")
        val time: String,
        @SerializedName("date")
        val date: String,
    ) {
        companion object {
            val diff = object : DiffCallback<Data>() {
                override fun areItemsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Data,
                    newItem: Data
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}
