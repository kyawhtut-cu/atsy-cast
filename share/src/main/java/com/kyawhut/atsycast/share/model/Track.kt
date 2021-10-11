package com.kyawhut.atsycast.share.model

import java.io.Serializable

data class Track(
    var name: String = "",
    var isSelected: Boolean = false,
) : Serializable
