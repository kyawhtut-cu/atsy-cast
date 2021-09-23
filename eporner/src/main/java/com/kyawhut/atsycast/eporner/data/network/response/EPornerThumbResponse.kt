package com.kyawhut.atsycast.eporner.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/22/21
 */
@Keep
internal data class EPornerThumbResponse(
    @SerializedName("src")
    val src: String
) : Serializable
