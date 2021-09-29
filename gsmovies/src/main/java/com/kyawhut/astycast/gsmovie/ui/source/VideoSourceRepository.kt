package com.kyawhut.astycast.gsmovie.ui.source

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal interface VideoSourceRepository {

    fun isHasResume(route: String, videoID: Int): Boolean
}
