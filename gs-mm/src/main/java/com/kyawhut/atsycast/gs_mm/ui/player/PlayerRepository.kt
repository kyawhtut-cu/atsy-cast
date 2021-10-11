package com.kyawhut.atsycast.gs_mm.ui.player

import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/8/21
 */
interface PlayerRepository {

    fun getLastPosition(route: String, videoID: String, isResume: Boolean): Long

    fun insertLastPosition(block: RecentlyWatchEntity.Builder.() -> Unit)

    fun deleteRecentlyWatch(route: String, videoID: String)
}
