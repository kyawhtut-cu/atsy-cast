package com.kyawhut.atsycast.msys.utils.cloudmd.tasks

import androidx.annotation.Nullable
import com.kyawhut.atsycast.msys.utils.cloudmd.Media

data class StreamTask(
    val isSuccessful: Boolean,
    @Nullable private val _streams: ArrayList<Media>?,
    @Nullable private val _exception: Exception?
) {
    val streams get() = _streams!!
    val exception: Exception get() = _exception!!
}
