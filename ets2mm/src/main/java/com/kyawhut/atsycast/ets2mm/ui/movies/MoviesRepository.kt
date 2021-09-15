package com.kyawhut.atsycast.ets2mm.ui.movies

import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface MoviesRepository {

    suspend fun getMovies(
        genresID: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
