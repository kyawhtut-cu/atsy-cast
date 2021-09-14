package com.kyawhut.atsycast.msubpc.ui.search

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.SearchResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: MSubAPI
) : SearchRepository {

    private var searchResponse: SearchResponse? = null
    private var isFirstTime: Boolean = false

    override suspend fun search(
        query: String,
        apiKey: String,
        callback: (NetworkResponse<List<Pair<String, List<VideoResponse>>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        isFirstTime = searchResponse == null
        if (searchResponse == null) {
            val response = execute { api.search(apiKey) }
            if (response.isSuccess) {
                searchResponse = response.data
            } else if (response.isError) {
                NetworkResponse.error(response.error, callback)
                return
            }
        }
        searchResponse?.let {
            val movies = it.movies.map {
                it.apply {
                    if (isFirstTime)
                        videoTitle = AesEncryptDecrypt.getDecryptedString(videoTitle)
                    isMovies = true
                }
            }.filter {
                it.videoTitle.contains(query, true)
            }
            val series = it.series.map {
                it.apply {
                    if (isFirstTime)
                        videoTitle = AesEncryptDecrypt.getDecryptedString(videoTitle)
                    isMovies = false
                }
            }.filter {
                it.videoTitle.contains(query, true)
            }
            if (!isFirstTime) delay(3000)
            NetworkResponse.success(
                listOf(
                    "Movies" to movies,
                    "Series" to series
                ),
                callback
            )
        }
    }
}
