package com.kyawhut.atsycast.msys.ui.source

import com.kyawhut.atsycast.msys.data.network.MsysAPI
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.SourceType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal class VideoSourceRepositoryImpl @Inject constructor(
    private val api: MsysAPI,
    private val recentlyWatch: RecentlyWatchSource,
    private val crashlytics: Crashlytics,
) : VideoSourceRepository {

    override fun isHasResume(videoID: String): Boolean {
        return recentlyWatch.get(videoID, SourceType.MSYS) != null
    }

    override suspend fun getRedirectURL(url: String, callback: (Boolean, String) -> Unit) {
        try {
            val response = api.getRedirectURL(url)
            CoroutineScope(Dispatchers.Main).launch {
                callback(
                    true,
                    response.raw().header("Location")
                        ?: "URL is empty. Please contact to admin."
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            crashlytics.sendCrashlytics(e)
            CoroutineScope(Dispatchers.Main).launch {
                callback(false, "Something went wrong. Please contact to admin.")
            }
        }
    }
}
