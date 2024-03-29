package com.kyawhut.atsycast.zcm.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
data class MoviesSourceResponse(
    @SerializedName("id")
    val sourceID: Int,
    @SerializedName("title")
    val sourceTitle: String,
    @SerializedName("quality")
    val quality: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("type")
    val sourceType: String,
    @SerializedName("url")
    val sourceURL: String,
) : Serializable
