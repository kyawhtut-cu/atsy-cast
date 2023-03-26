package com.kyawhut.atsycast.msubpc.ui.series

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class SeriesRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val crashlytics: Crashlytics,
) : SeriesRepository {

    private var _tmpSeriesData = mutableListOf<VideoResponse>()
    private var _newEpisodes = mutableListOf<VideoResponse>()
    private var isFirstTime: Boolean = false

    override suspend fun getSeries(
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        isFirstTime = key == "-" || _tmpSeriesData.isEmpty()
        if (isFirstTime) {
            _newEpisodes.clear()
            _tmpSeriesData.clear()
            val response = execute(crashlytics) {
                api.getAllSeries()
            }
            if (response.isSuccess) {
                response.data?.series?.map {
                    it.apply {
                        isMovies = false
                    }
                }?.let {
                    _tmpSeriesData.addAll(it)
                }
                response.data?.newepisodes?.map {
                    it.apply {
                        isMovies = false
                    }
                }?.let {
                    _newEpisodes.addAll(it)
                }
            } else if (response.isError) {
                withContext(Dispatchers.Main) {
                    callback(NetworkResponse(response.networkStatus, null, response.error))
                }
                return
            }
        }

        when (key) {
            "new-episode" -> _newEpisodes
            else -> _tmpSeriesData
        }.filter {
            when (key) {
                "-", "new-episode" -> true
                "ongoing" -> it.videoSeasonNumber == 99
                "complete" -> it.videoSeasonNumber == 0
                "new-season" -> it.videoSeasonNumber != 0 && it.videoSeasonNumber != 99 && !_newEpisodes.any { new -> new.videoId == it.videoId }
                "korea", "english", "thai" -> key.contains(
                    (it.videoLanguage ?: "").toLowerCase(Locale.ENGLISH)
                )

                else -> (it.videoGenres?.replace(" ", "")?.split(",") ?: listOf()).any { g ->
                    key.contains(g.toLowerCase())
                }
            }
        }.run {
            if (!isFirstTime) delay(3000)
            NetworkResponse.success(this, callback)
        }
    }
}
