package com.kyawhut.atsycast.share.utils

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object ShareUtils {

    const val PLAYER_MP4_EXTENSION = "mp4"
    const val PLAYER_M3U8_EXTENSION = "m3u8"
    const val PLAYER_MKV_EXTENSION = "mkv"
    const val PLAYER_MOV_EXTENSION = "mov"
    const val PLAYER_WEBM_EXTENSION = "webm"
    const val PLAYER_EMBED_EXTENSION = "embed"
    const val PLAYER_YOUTUBE_EXTENSION = "youtube"

    val String.isDirectPlayExtension: Boolean
        get() = this == PLAYER_MP4_EXTENSION || this == PLAYER_M3U8_EXTENSION || this == PLAYER_MKV_EXTENSION || this == PLAYER_MOV_EXTENSION

    val String.isWEBMExtension: Boolean
        get() = this == PLAYER_WEBM_EXTENSION

    val String.isEmbedExtension: Boolean
        get() = this == PLAYER_EMBED_EXTENSION

    val String.isYoutubeExtension: Boolean
        get() = this == PLAYER_YOUTUBE_EXTENSION

    fun FragmentActivity.getColorValue(color: Int): Int = ContextCompat.getColor(this, color)
}
