package com.kyawhut.atsycast.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.extension.Extension.devicePassword
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class HomeRepositoryImpl @Inject constructor(
    private val sheetAPI: SheetAPI,
    private val watchLater: WatchLaterSource,
    private val recentlyWatch: RecentlyWatchSource,
    private val crashlytics: Crashlytics,
) : HomeRepository {

    override val cacheDB: LiveData<Pair<List<WatchLaterEntity>, List<RecentlyWatchEntity>>>
        get() = Flowable.zip(
            watchLater.getAll(),
            recentlyWatch.getAll()
        ) { watchLater, recentlyWatch ->
            watchLater to recentlyWatch
        }.toLiveData()

    override suspend fun getHomeFeatures(
        context: Context,
        callback: (NetworkResponse<HashMap<String, List<HomeFeatureResponse.Data>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val features = execute(crashlytics) {
            val data = HashMap<String, List<HomeFeatureResponse.Data>>()
            sheetAPI.getHomeFeature(scriptRequest {
                route = "homeFeatures"
                payload = mutableMapOf(
                    "device_id" to context.deviceID,
                    "device_password" to context.devicePassword
                )
            }).data.forEach { (key, value) ->
                data[key] = value.filter {
                    it.featureStatus
                }
            }
            data
        }
        features.post(callback)
    }

    override suspend fun getFeatureDetail(
        data: WatchLaterEntity,
        callback: (NetworkResponse<Pair<WatchLaterEntity, HomeFeatureResponse.Data?>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val featureDetail = execute(crashlytics) {
            sheetAPI.getFeatureDetail(scriptRequest {
                route = "featureDetail"
                payload = mutableMapOf(
                    "feature_id" to data.videoSourceType.featureID
                )
            })
        }
        if (featureDetail.isSuccess) {
            NetworkResponse.success((data to featureDetail.data?.data), callback)
        } else NetworkResponse.error(featureDetail.error, callback)
    }

    override suspend fun getFeatureDetail(
        data: RecentlyWatchEntity,
        callback: (NetworkResponse<Pair<RecentlyWatchEntity, HomeFeatureResponse.Data?>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val featureDetail = execute(crashlytics) {
            sheetAPI.getFeatureDetail(scriptRequest {
                route = "featureDetail"
                payload = mutableMapOf(
                    "feature_id" to data.videoSourceType.featureID
                )
            })
        }
        if (featureDetail.isSuccess) {
            NetworkResponse.success((data to featureDetail.data?.data), callback)
        } else NetworkResponse.error(featureDetail.error, callback)
    }
}
