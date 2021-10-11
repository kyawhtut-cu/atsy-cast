package com.kyawhut.atsycast.gs_mm.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(route: String): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(route: String): LiveData<List<VideoResponse>>

}
