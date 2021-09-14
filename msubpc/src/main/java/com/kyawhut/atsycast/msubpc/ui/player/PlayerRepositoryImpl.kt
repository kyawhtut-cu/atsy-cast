package com.kyawhut.atsycast.msubpc.ui.player

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

    override fun getLastPosition(videoID: Int, isResume: Boolean): Long {
        if (!isResume) return 0L
        return recentlyWatch.get("$videoID", SourceType.MSUB_PC)?.videoLastPosition ?: 0L
    }

    override fun insertLastPosition(block: RecentlyWatchEntity.Builder.() -> Unit) {
        recentlyWatch.insert(block)
    }

    override fun deleteRecentlyWatch(videoID: Int) {
        recentlyWatch.delete("$videoID", SourceType.MSUB_PC)
    }
}
