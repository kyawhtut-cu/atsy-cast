package com.kyawhut.atsycast.share.model

import com.kyawhut.atsycast.share.utils.ShareUtils
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
data class VideoSourceModel(
    val sourceId: String,
    val title: String,
    val description: String? = null,
    var url: String,
    val agent: String? = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36",
    val cookies: String? = null,
    val customHeader: List<Pair<String, String>> = mutableListOf(),
    val sourceType: String = ShareUtils.PLAYER_MP4_EXTENSION,
    val subTitle: List<SubTitleModel> = mutableListOf()
) : Serializable
