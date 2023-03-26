package com.kyawhut.atsycast.msubpc.ui.movies

import android.content.Context
import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import kotlinx.coroutines.delay
import java.util.Locale
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class MoviesRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val crashlytics: Crashlytics,
) : MoviesRepository {

    private var _tmpMoviesData = mutableListOf<VideoResponse>()
    private var isFirstTime: Boolean = false

    override suspend fun getMovies(
        context: Context,
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        isFirstTime = key == "-" || _tmpMoviesData.isEmpty()
        if (isFirstTime) {
            _tmpMoviesData.clear()
            val response = execute(crashlytics) { api.getAllMovies().movies }
            if (response.isSuccess) {
                response.data?.map {
                    it.apply {
                        isMovies = true
                    }
                }?.let {
                    _tmpMoviesData.addAll(it)
                }
            } else if (response.isError) {
                response.post(callback)
                return
            }
        }

        _tmpMoviesData.filter {
            when (key) {
                "-" -> true
                "2019", "2020", "2021", "2022", "2023" -> it.videoYear == key
                "japan,korea,thai", "india", "china" -> key.contains(
                    (it.videoLanguage ?: "").toLowerCase(Locale.ENGLISH)
                )

                "horror,thriller" -> with(
                    it.videoGenres?.toLowerCase(Locale.ENGLISH) ?: ""
                ) {
                    contains("horror") || contains("thriller")
                }

                else -> (it.videoGenres?.replace(" ", "")?.split(",") ?: listOf()).any { g ->
                    key.contains(g.toLowerCase())
                }
            }
        }.run {
            if (!isFirstTime) delay(3000)
            NetworkResponse.success(this.filter {
                !(it.videoGenres ?: "").isAdult || context.isAdultOpen
            }, callback)
        }
    }
}
