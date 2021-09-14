package com.kyawhut.atsycast.msys.ui.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.google.gson.Gson
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
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

    override fun getRecentlyWatch(): LiveData<List<RecentlyWatchEntity>> {
        return recently.getLive(SourceType.MSYS)
    }

    override fun getWatchLater(): LiveData<List<MoviesResponse>> {
        return watchLater.getLive(SourceType.MSYS).map<List<MoviesResponse>> {
            it.map {
                Gson().fromJson(it.videoData, MoviesResponse::class.java)
            }
        }.toLiveData()
    }
}
