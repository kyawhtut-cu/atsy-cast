package com.kyawhut.atsycast.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepository
) : BaseViewModel() {

    var homeFeatureList: HashMap<String, List<HomeFeatureResponse.Data>> = hashMapOf()

    val cacheDB: LiveData<Pair<List<WatchLaterEntity>, List<RecentlyWatchEntity>>> by lazy {
        repo.cacheDB
    }

    fun getHomeFeatures(
        context: Context,
        callback: (NetworkResponse<HashMap<String, List<HomeFeatureResponse.Data>>>) -> Unit
    ) {
        viewModelScope {
            repo.getHomeFeatures(context, callback)
        }
    }

    fun getFeatureDetail(
        data: WatchLaterEntity,
        callback: (NetworkResponse<Pair<WatchLaterEntity, HomeFeatureResponse.Data?>>) -> Unit
    ) {
        viewModelScope {
            repo.getFeatureDetail(data, callback)
        }
    }

    fun getFeatureDetail(
        data: RecentlyWatchEntity,
        callback: (NetworkResponse<Pair<RecentlyWatchEntity, HomeFeatureResponse.Data?>>) -> Unit
    ) {
        viewModelScope {
            repo.getFeatureDetail(data, callback)
        }
    }
}
