package com.kyawhut.atsycast.msys.ui.genres

import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface MoviesRepository {

    suspend fun getMovies(
        genresID: Int,
        apiKey: String,
        page: Int,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    )
}
