package com.kyawhut.atsycast.zcm.ui.player

import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
class PlayerRepositoryImpl @Inject constructor(
    private val recentlyWatch: RecentlyWatchSource
) : PlayerRepository {

    override fun getLastPosition(videoID: String, isResume: Boolean): Long {
        if (!isResume) return 0L
        return recentlyWatch.get(videoID, SourceType.ZCM)?.videoLastPosition ?: 0L
    }

    override fun insertLastPosition(block: RecentlyWatchEntity.Builder.() -> Unit) {
        recentlyWatch.insert(block)
    }

    override fun deleteRecentlyWatch(videoID: String) {
        recentlyWatch.delete(videoID, SourceType.ZCM)
    }
}
