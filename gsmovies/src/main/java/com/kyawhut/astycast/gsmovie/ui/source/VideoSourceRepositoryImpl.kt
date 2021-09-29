package com.kyawhut.astycast.gsmovie.ui.source

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

    override fun isHasResume(route: String, videoID: Int): Boolean {
        return recentlyWatch.get("$videoID", SourceType.getSourceType(route)) != null
    }
}