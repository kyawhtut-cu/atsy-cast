package com.kyawhut.atsycast.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/6/21
 */
@Keep
data class FeatureDetailResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: HomeFeatureResponse.Data?,
)
