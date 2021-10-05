package com.kyawhut.atsycast.share.network.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@Keep
data class ScriptRequest private constructor(
    @SerializedName("route")
    val route: String,
    @SerializedName("payload")
    val payload: Map<String, Any>
) {

    class Builder {
        var route: String = ""
        var payload: MutableMap<String, Any> = mutableMapOf()

        fun build(): ScriptRequest {
            return ScriptRequest(route, payload)
        }
    }
}

fun scriptRequest(block: ScriptRequest.Builder.() -> Unit) =
    ScriptRequest.Builder().also(block).build()
