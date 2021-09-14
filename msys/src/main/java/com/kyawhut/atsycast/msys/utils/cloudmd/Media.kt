package com.kyawhut.atsycast.msys.utils.cloudmd

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val quality: String,
    val resolution: String,
    val fileSize: String,
    val url: String,
    internal val downloadRoute: String
) : Parcelable {
    override fun toString(): String {
        return quality
    }
}
