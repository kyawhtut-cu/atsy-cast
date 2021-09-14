package com.kyawhut.atsycast.msubpc.ui.home

import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
class HomeRepositoryImpl @Inject constructor(
    private val recentlyWatchSource: RecentlyWatchSource,
    private val watchLaterSource: WatchLaterSource
) : HomeRepository {

    override val isHasRecently: Boolean
        get() = recentlyWatchSource.isHasRecentlyWatch(SourceType.MSUB_PC)

    override val isHasWatchLater: Boolean
        get() = watchLaterSource.isHasWatchLater(SourceType.MSUB_PC)
}
