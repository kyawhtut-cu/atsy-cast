package com.kyawhut.atsycast.msys.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(): LiveData<List<MoviesResponse>>

}
