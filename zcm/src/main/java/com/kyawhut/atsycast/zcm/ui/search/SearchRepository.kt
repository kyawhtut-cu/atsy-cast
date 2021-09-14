package com.kyawhut.atsycast.zcm.ui.search

import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse

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