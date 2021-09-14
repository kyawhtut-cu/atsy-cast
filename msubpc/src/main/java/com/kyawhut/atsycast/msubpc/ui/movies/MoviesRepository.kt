package com.kyawhut.atsycast.msubpc.ui.movies

import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal interface MoviesRepository {

    suspend fun getMovies(
        key: String,
        apiKey: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
