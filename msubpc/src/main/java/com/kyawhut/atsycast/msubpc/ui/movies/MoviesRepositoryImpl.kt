package com.kyawhut.atsycast.msubpc.ui.movies

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class MoviesRepositoryImpl @Inject constructor(
    private val api: MSubAPI
) : MoviesRepository {

    private var _tmpMoviesData = mutableListOf<VideoResponse>()
    private var isFirstTime: Boolean = false

    override suspend fun getMovies(
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        isFirstTime = key == "-" || _tmpMoviesData.isEmpty()
        if (key == "-" || _tmpMoviesData.isEmpty()) {
            _tmpMoviesData.clear()
            val response = execute { api.getAllMovies() }
            if (response.isSuccess) {
                response.data?.map {
                    it.apply {
                        videoTitle = AesEncryptDecrypt.getDecryptedString(videoTitle)
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
                "2019", "2020", "2021" -> it.videoYear == key
                "japan,korea,thai", "india", "china" -> key.contains(
                    (it.videoLanguage ?: "").toLowerCase(Locale.ENGLISH)
                )
                "horror,thriller" -> with(
                    it.videoGenres?.toLowerCase(Locale.ENGLISH) ?: ""
                ) {
                    contains("horror") || contains("thriller")
                }
                else -> (it.videoGenres ?: "").toLowerCase(Locale.ENGLISH)
                    .contains(key) || key.contains(
                    (it.videoGenres ?: "").toLowerCase(Locale.ENGLISH)
                )
            }
        }.run {
            if (!isFirstTime) delay(3000)
            NetworkResponse.success(this, callback)
        }
    }
}
