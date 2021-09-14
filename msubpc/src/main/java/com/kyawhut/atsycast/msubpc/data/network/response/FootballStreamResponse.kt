package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal data class FootballStreamResponse(
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
)
