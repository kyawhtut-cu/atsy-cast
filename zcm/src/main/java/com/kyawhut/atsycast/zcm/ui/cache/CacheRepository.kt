package com.kyawhut.atsycast.zcm.ui.cache

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(): LiveData<List<MoviesResponse>>

}
