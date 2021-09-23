package com.kyawhut.atsycast.eporner.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/22/21
 */
@Keep
internal data class EPornerResponse(
    @SerializedName("count")
    val count: String,
    @SerializedName("videos")
    val videoList: List<EPornerVideoResponse>
)
