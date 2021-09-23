package com.kyawhut.atsycast.twod.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@Keep
data class TwoDResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("live")
    val isLive: Boolean,
    @SerializedName("am")
    val am: TwoDAMPMResponse,
    @SerializedName("pm")
    val pm: TwoDAMPMResponse
)
