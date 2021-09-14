package com.kyawhut.atsycast.msys.ui.detail

import com.google.gson.Gson
import com.kyawhut.atsycast.msys.data.network.MsysAPI
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.data.network.response.SeasonResponse
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: MsysAPI,
    private val watchLater: WatchLaterSource,
) : DetailRepository {

    private fun getWatchLater(moviesID: Int): WatchLaterEntity? {
        val watchLater = watchLater.get(SourceType.MSYS)
        val index = watchLater.map {
            Gson().fromJson(it.videoData, MoviesResponse::class.java)
        }.indexOfFirst {
            it.moviesID == moviesID
        }
        return if (index == -1) null else watchLater[index]
    }

    override fun isWatchLater(moviesID: Int): Boolean {
        return getWatchLater(moviesID) != null
    }

    override fun toggleWatchLater(videoData: MoviesResponse) {
        val watchLaterEntity = getWatchLater(videoData.moviesID)
        if (watchLaterEntity != null) watchLater.delete(watchLaterEntity.id, SourceType.MSYS)
        else watchLater.insert {
            this.videoData = Gson().toJson(videoData)
            videoSourceType = SourceType.MSYS
        }
    }

    override suspend fun getRelatedMovies(
        apiKey: String,
        genresID: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getRelated(genresID, apiKey) }
        response.post(callback)
    }

    override suspend fun getSeriesSeason(
        apiKey: String,
        seriesID: Int,
        callback: (NetworkResponse<List<SeasonResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getSeriesSeason(seriesID, apiKey) }
        response.post(callback)
    }
}
