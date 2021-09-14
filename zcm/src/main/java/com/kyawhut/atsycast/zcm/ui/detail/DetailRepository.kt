package com.kyawhut.atsycast.zcm.ui.detail

import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.data.network.response.SeasonResponse

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
