package com.kyawhut.astycast.gsmovie.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@Keep
internal data class VideoSourceResponse(
    @SerializedName("quality")
    val quality: String,
    @SerializedName("url")
    val videoURL: String
) : Serializable
