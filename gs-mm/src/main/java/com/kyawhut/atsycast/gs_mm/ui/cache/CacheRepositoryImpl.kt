package com.kyawhut.atsycast.gs_mm.ui.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class CacheRepositoryImpl @Inject constructor(
    private val recently: RecentlyWatchSource,
    private val watchLater: WatchLaterSource
) : CacheRepository {

    override fun getRecentlyWatch(route: String): LiveData<List<RecentlyWatchEntity>> {
        return recently.getLive(SourceType.getSourceType(route)).toLiveData()
    }

    override fun getWatchLater(route: String): LiveData<List<VideoResponse>> {
        return watchLater.getLive(SourceType.getSourceType(route)).map<List<VideoResponse>> {
            it.map {
                it.getData()
            }
        }.toLiveData()
    }
}
