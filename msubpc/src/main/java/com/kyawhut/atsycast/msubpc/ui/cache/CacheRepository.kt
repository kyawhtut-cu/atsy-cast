package com.kyawhut.atsycast.msubpc.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(): LiveData<List<VideoResponse>>

}
