package com.kyawhut.atsycast.msubpc.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@Keep
data class MovieStreamResponse(
    @SerializedName("stream")
    var stream: String?,
    @SerializedName("vstream")
    var vStream: String?,
)
