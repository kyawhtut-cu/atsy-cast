package com.kyawhut.atsycast.msys.utils.cloudmd.tasks

import androidx.annotation.Nullable

data class DirectLinkTask(
    val isSuccessful: Boolean,
    @Nullable private val _url: String?,
    @Nullable private val _exception: Exception?
) {
    val url: String get() = _url!!
    val exception: Exception get() = _exception!!
}
