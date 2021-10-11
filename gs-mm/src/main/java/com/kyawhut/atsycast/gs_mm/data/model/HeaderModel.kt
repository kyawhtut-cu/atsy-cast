package com.kyawhut.atsycast.gs_mm.data.model

import androidx.leanback.widget.HeaderItem

/**
 * @author kyawhtut
 * @date 10/9/21
 */
data class HeaderModel(
    val _id: Long, val _name: String, val type: String = ""
) : HeaderItem(_id, _name)
