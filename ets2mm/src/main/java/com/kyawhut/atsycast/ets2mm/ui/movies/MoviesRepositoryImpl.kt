package com.kyawhut.atsycast.ets2mm.ui.movies

import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class MoviesRepositoryImpl @Inject constructor(
    private val api: Et2API
) : MoviesRepository {

    override suspend fun getMovies(
        genresID: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute {
            when (genresID) {
                "-1" -> api.getLatestMovies(page)
                "0" -> api.getLatestTvSeries(page)
                else -> api.getVideoByGenresID(genresID, page)
            }
        }
        response.post(callback)
    }
}
