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
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val watchLater: WatchLaterSource,
    private val crashlytics: Crashlytics,
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
        callback: (NetworkResponse<MovieStreamResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getMovieStream("$videoID") }
        if (response.isSuccess) {
            response.data?.apply {
                if (movieStream != null) movieStream = AesEncryptDecrypt.getDecryptedString(
                    movieStream
                )
                if (movieVStream != null) movieVStream = AesEncryptDecrypt.getDecryptedString(
                    movieVStream
                )
                if (movieVBackup != null) movieVBackup = AesEncryptDecrypt.getDecryptedString(
                    movieVBackup
                )
                if (movieFreeMium != null) movieFreeMium = AesEncryptDecrypt.getDecryptedString(
                    movieFreeMium
                )
            }
        }
        response.post(callback)
    }

    override suspend fun getRelatedMovies(
        genres: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        if (genres.isEmpty()) return
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getRelatedMovies(genres.substring(0, 4)) }
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
        callback: (NetworkResponse<Pair<List<VideoResponse>, List<EpisodeResponse>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val relatedSeason = execute(crashlytics) { api.getRelatedSeason(seasonID) }
        val episodeResponse = execute(crashlytics) { api.getSeriesEpisode(seasonID) }
        if (episodeResponse.isError) {
            NetworkResponse.error(
                episodeResponse.error,
                callback
            )
        } else {
            relatedSeason.data?.map {
                it.apply {
                    isMovies = false
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
                    if (it.vbackup != null) it.vbackup = AesEncryptDecrypt.getDecryptedString(
                        it.vbackup
                    )
                    if (it.freemium != null) it.freemium = AesEncryptDecrypt.getDecryptedString(
                        it.freemium
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
