package com.kyawhut.atsycast.share.model

import com.kyawhut.atsycast.share.utils.ShareUtils
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
data class VideoSourceModel(
    val sourceId: Int,
    val title: String,
    val description: String? = null,
    var url: String,
    val agent: String? = null,
    val cookies: String? = null,
    val customHeader: List<Pair<String, String>> = mutableListOf(),
    val sourceType: String = ShareUtils.PLAYER_MP4_EXTENSION,
) : Serializable
