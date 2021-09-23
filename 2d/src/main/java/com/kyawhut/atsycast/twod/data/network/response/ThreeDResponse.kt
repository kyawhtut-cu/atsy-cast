package com.kyawhut.atsycast.twod.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@Keep
data class ThreeDResponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("three_d")
    val threeD: String,
    @SerializedName("updated_at")
    val update: String
)
