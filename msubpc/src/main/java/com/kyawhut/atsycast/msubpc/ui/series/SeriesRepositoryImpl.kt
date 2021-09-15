package com.kyawhut.atsycast.msubpc.ui.series

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
internal class SeriesRepositoryImpl @Inject constructor(
    private val api: MSubAPI
) : SeriesRepository {

    private var _tmpSeriesData = mutableListOf<VideoResponse>()
    private var isFirstTime: Boolean = false

    override suspend fun getSeries(
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        isFirstTime = key == "-" || _tmpSeriesData.isEmpty()
        if (key == "-" || _tmpSeriesData.isEmpty()) {
            _tmpSeriesData.clear()
            val response = execute { api.getAllSeries() }
            if (response.isSuccess) {
                response.data?.map {
                    it.apply {
                        isMovies = false
                        videoTitle = AesEncryptDecrypt.getDecryptedString(videoTitle)
                    }
                }?.let {
                    _tmpSeriesData.addAll(it)
                }
            } else if (response.isError) {
                response.post(callback)
                return
            }
        }

        _tmpSeriesData.filter {
            when (key) {
                "-" -> true
                "korea", "english" -> key.contains(
                    (it.videoLanguage ?: "").toLowerCase(Locale.ENGLISH)
                )
                else -> (it.videoGenres ?: "").contains(key, ignoreCase = true) || key.contains(
                    (it.videoGenres ?: ""),
                    ignoreCase = true
                )
            }
        }.run {
            if (!isFirstTime) delay(3000)
            NetworkResponse.success(this, callback)
        }
    }
}
