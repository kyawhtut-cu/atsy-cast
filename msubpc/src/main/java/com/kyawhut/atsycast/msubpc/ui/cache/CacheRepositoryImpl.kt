package com.kyawhut.atsycast.msubpc.ui.cache

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.google.gson.Gson
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.SourceType
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class CacheRepositoryImpl @Inject constructor(
    private val recently: RecentlyWatchSource,
    private val watchLater: WatchLaterSource
) : CacheRepository {

    override fun getRecentlyWatch(context: Context): LiveData<List<RecentlyWatchEntity>> {
        return recently.getLive(SourceType.MSUB_PC).map {
            it.filter {
                !it.isAdult || context.isAdultOpen
            }
        }.toLiveData()
    }

    override fun getWatchLater(context: Context): LiveData<List<VideoResponse>> {
        return watchLater.getLive(SourceType.MSUB_PC).map<List<VideoResponse>> {
            it.map {
                Gson().fromJson(it.videoData, VideoResponse::class.java)
            }.filter {
                !(it.videoGenres ?: "").isAdult || context.isAdultOpen
            }
        }.toLiveData()
    }
}
