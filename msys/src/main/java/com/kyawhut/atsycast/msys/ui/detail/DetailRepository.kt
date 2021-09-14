package com.kyawhut.atsycast.msys.ui.detail

import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.data.network.response.SeasonResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface DetailRepository {

    fun isWatchLater(moviesID: Int): Boolean

    fun toggleWatchLater(videoData: MoviesResponse)

    suspend fun getRelatedMovies(
        apiKey: String,
        genresID: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    )

    suspend fun getSeriesSeason(
        apiKey: String,
        seriesID: Int,
        callback: (NetworkResponse<List<SeasonResponse>>) -> Unit
    )
}
