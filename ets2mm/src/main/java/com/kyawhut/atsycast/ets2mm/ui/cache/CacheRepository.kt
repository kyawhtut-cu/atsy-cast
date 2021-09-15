package com.kyawhut.atsycast.ets2mm.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(): LiveData<List<VideoResponse>>

}
