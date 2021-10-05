package com.kyawhut.astycast.gsmovie.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(route: String): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(route: String): LiveData<List<VideoResponse.Data>>

}
