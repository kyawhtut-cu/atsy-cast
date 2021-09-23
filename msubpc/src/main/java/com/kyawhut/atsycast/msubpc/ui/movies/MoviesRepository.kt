package com.kyawhut.atsycast.msubpc.ui.movies

import android.content.Context
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal interface MoviesRepository {

    suspend fun getMovies(
        context: Context,
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
