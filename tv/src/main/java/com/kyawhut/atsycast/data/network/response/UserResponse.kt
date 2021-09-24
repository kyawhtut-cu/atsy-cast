package com.kyawhut.atsycast.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@Keep
data class UserResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data?
) {

    @Keep
    data class Data(
        @SerializedName("display_name")
        val displayName: String,
        @SerializedName("device_id")
        val deviceID: String,
        @SerializedName("device_name")
        val deviceName: String,
    )
}
