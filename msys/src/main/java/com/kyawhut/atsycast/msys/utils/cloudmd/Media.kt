package com.kyawhut.atsycast.msys.utils.cloudmd

import java.io.Serializable

data class Media(
    val quality: String,
    val resolution: String,
    val fileSize: String,
    val url: String,
    internal val downloadRoute: String
) : Serializable {
    override fun toString(): String {
        return quality
    }
}
