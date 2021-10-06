package com.kyawhut.atsycast.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface HomeRepository {

    suspend fun getHomeFeatures(
        context: Context,
        callback: (NetworkResponse<HashMap<String, List<HomeFeatureResponse.Data>>>) -> Unit
    )

    val cacheDB: LiveData<Pair<List<WatchLaterEntity>, List<RecentlyWatchEntity>>>

    suspend fun getFeatureDetail(
        data: WatchLaterEntity,
        callback: (NetworkResponse<Pair<WatchLaterEntity, HomeFeatureResponse.Data?>>) -> Unit
    )

    suspend fun getFeatureDetail(
        data: RecentlyWatchEntity,
        callback: (NetworkResponse<Pair<RecentlyWatchEntity, HomeFeatureResponse.Data?>>) -> Unit
    )
}
