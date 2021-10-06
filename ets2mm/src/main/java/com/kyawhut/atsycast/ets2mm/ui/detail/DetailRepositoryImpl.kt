package com.kyawhut.atsycast.ets2mm.ui.detail

import com.google.gson.Gson
import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: Et2API,
    private val watchLater: WatchLaterSource,
    private val crashlytics: Crashlytics,
) : DetailRepository {

    private fun getWatchLater(moviesID: Int): WatchLaterEntity? {
        val watchLater = watchLater.get(SourceType.ET2SMM)
        val index = watchLater.map {
            Gson().fromJson(it.videoData, VideoResponse::class.java)
        }.indexOfFirst {
            it.videoID == moviesID
        }
        return if (index == -1) null else watchLater[index]
    }

    override fun isWatchLater(moviesID: Int): Boolean {
        return getWatchLater(moviesID) != null
    }

    override fun toggleWatchLater(videoData: VideoResponse) {
        val watchLaterEntity = getWatchLater(videoData.videoID)
        if (watchLaterEntity != null) watchLater.delete(watchLaterEntity.id, SourceType.ET2SMM)
        else watchLater.insert {
            this.videoData = Gson().toJson(videoData)
            videoSourceType = SourceType.ET2SMM
        }
    }

    override suspend fun getVideoDetail(
        videoID: Int,
        isMovies: Boolean,
        callback: (NetworkResponse<VideoDetailResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getVideoDetail(if (isMovies) "movie" else "tvseries", videoID)
        }
        response.post(callback)
    }
}
