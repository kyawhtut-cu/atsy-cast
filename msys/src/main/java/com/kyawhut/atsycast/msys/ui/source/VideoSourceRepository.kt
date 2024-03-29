package com.kyawhut.atsycast.msys.ui.source

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal interface VideoSourceRepository {

    fun isHasResume(videoID: String): Boolean

    suspend fun getRedirectURL(url: String, callback: (Boolean, String) -> Unit)
}
