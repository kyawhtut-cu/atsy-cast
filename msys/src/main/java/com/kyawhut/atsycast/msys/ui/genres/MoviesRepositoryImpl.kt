package com.kyawhut.atsycast.msys.ui.genres

import com.kyawhut.atsycast.msys.data.network.MsysAPI
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class MoviesRepositoryImpl @Inject constructor(
    private val api: MsysAPI
) : MoviesRepository {

    override suspend fun getMovies(
        genresID: Int,
        apiKey: String,
        page: Int,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getMovies(genresID, apiKey, page) }
        response.post(callback)
    }
}
