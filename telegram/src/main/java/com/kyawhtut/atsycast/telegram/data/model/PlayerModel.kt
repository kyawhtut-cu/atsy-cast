package com.kyawhtut.atsycast.telegram.data.model

import com.kyawhut.atsycast.share.model.VideoSourceModel

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
data class PlayerModel(
    val videoID: String,
    val videoTitle: String,
    val videoThumbnail: String,
    val videoSource: VideoSourceModel
)
