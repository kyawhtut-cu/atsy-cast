package com.kyawhut.atsycast.twod.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/20/21
 */
@Keep
data class TwoDAMPMResponse(
    @SerializedName("set")
    val set: String?,
    @SerializedName("val")
    val value: String?,
    @SerializedName("two_d")
    val twoD: String?,
    @SerializedName("modern")
    val modern: String?,
    @SerializedName("internet")
    val internet: String?,
    @SerializedName("updated_at")
    val update: String?
)
