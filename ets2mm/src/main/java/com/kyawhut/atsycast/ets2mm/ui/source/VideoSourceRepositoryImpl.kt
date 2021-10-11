package com.kyawhut.atsycast.ets2mm.ui.source

import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal class VideoSourceRepositoryImpl @Inject constructor(
    private val recentlyWatch: RecentlyWatchSource
) : VideoSourceRepository {

    override fun isHasResume(videoID: String): Boolean {
        return recentlyWatch.get(videoID, SourceType.ET2SMM) != null
    }
}
