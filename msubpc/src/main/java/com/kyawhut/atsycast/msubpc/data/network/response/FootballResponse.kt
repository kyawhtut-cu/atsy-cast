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
    @SerializedName("id")
    val id: Int,
    @SerializedName("league")
    val league: String,
    @SerializedName("op")
    val op: String,
    @SerializedName("op2")
    val op2: String,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("flag2")
    val flag2: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("date")
    val date: String,
) {
    companion object {
        val diff = object : DiffCallback<FootballResponse>() {
            override fun areItemsTheSame(
                oldItem: FootballResponse,
                newItem: FootballResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FootballResponse,
                newItem: FootballResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    val matchName: String
        get() = "%s %s %s".format(
            op,
            "VS",
            op2
        )
}
