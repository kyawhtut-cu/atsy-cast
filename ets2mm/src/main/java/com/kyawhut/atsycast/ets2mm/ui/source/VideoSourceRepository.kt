package com.kyawhut.atsycast.ets2mm.ui.source

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal interface VideoSourceRepository {

    fun isHasResume(videoID: Int): Boolean
}
