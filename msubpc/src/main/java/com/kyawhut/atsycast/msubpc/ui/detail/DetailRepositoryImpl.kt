package com.kyawhut.atsycast.msubpc.ui.detail

import com.google.gson.Gson
import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MovieStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val watchLater: WatchLaterSource
) : DetailRepository {

    private fun getWatchLater(videoID: Int): WatchLaterEntity? {
        val watchLater = watchLater.get(SourceType.MSUB_PC)
        val index = watchLater.map {
            Gson().fromJson(it.videoData, VideoResponse::class.java)
        }.indexOfFirst {
            it.videoId == videoID
        }
        return if (index == -1) null else watchLater[index]
    }

    override fun isWatchLater(videoID: Int): Boolean {
        return getWatchLater(videoID) != null
    }

    override fun toggleWatchLater(videoData: VideoResponse) {
        val watchLaterEntity = getWatchLater(videoData.videoId)
        if (watchLaterEntity != null) watchLater.delete(watchLaterEntity.id, SourceType.MSUB_PC)
        else watchLater.insert {
            this.videoData = Gson().toJson(videoData)
            videoSourceType = SourceType.MSUB_PC
        }
    }

    override suspend fun getMovieStream(
        videoID: Int,
        apiKey: String,
        callback: (NetworkResponse<MovieStreamResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getMovieStream(apiKey, "$videoID") }
        if (response.isSuccess) {
            response.data?.apply {
                if (stream != null) stream = AesEncryptDecrypt.getDecryptedString(stream)
                if (vStream != null) vStream = AesEncryptDecrypt.getDecryptedString(vStream)
            }
        }
        response.post(callback)
    }

    override suspend fun getRelatedMovies(
        genres: String,
        apiKey: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        if (genres.isEmpty()) return
        NetworkResponse.loading(callback)
        val response = execute { api.getRelatedMovies(apiKey, genres.substring(0, 4)) }
        if (response.isSuccess) {
            NetworkResponse.success((response.data ?: listOf()).map {
                it.apply {
                    isMovies = true
                }
            }, callback)
        } else response.post(callback)
    }

    override suspend fun getSeasonEpisode(
        seasonID: Int,
        apiKey: String,
        callback: (NetworkResponse<Pair<List<VideoResponse>, List<EpisodeResponse>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val relatedSeason = execute { api.getRelatedSeason(apiKey, seasonID) }
        val episodeResponse = execute { api.getSeriesEpisode(apiKey, seasonID) }
        if (episodeResponse.isError) {
            NetworkResponse.error(
                episodeResponse.error,
                callback
            )
        } else {
            relatedSeason.data?.map {
                it.apply {
                    isMovies = false
                    videoTitle = AesEncryptDecrypt.getDecryptedString(videoTitle)
                }
            }
            episodeResponse.data?.map {
                it.apply {
                    if (it.stream != null) it.stream = AesEncryptDecrypt.getDecryptedString(
                        it.stream
                    )
                    if (it.vStream != null) it.vStream = AesEncryptDecrypt.getDecryptedString(
                        it.vStream
                    )
                }
            }
            NetworkResponse.success(
                (relatedSeason.data ?: listOf()) to (episodeResponse.data ?: listOf()),
                callback
            )
        }
    }
}
