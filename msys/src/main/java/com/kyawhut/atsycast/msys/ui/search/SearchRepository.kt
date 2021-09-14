package com.kyawhut.atsycast.msys.ui.search

import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        query: String,
        apiKey: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    )
}