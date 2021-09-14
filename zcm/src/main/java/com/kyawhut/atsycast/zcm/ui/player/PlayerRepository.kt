package com.kyawhut.atsycast.zcm.ui.player

import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/8/21
 */
interface PlayerRepository {

    fun getLastPosition(videoID: Int, isResume: Boolean): Long

    fun insertLastPosition(block: RecentlyWatchEntity.Builder.() -> Unit)

    fun deleteRecentlyWatch(videoID: Int)
}
