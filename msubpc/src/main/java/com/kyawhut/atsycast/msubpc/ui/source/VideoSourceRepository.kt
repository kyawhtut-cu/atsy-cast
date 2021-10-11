package com.kyawhut.atsycast.msubpc.ui.source

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal interface VideoSourceRepository {

    fun isHasResume(videoID: String): Boolean
}
