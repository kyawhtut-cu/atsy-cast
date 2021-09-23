package com.kyawhut.atsycast.zcm.ui.genres

import android.content.Context
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface MoviesRepository {

    suspend fun getMovies(
        context: Context,
        genresID: Int,
        apiKey: String,
        page: Int,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    )
}
