package com.kyawhut.atsycast.ets2mm.ui.search

import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: Et2API
) : SearchRepository {

    override suspend fun search(
        query: String,
        callback: (NetworkResponse<SearchResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.search(query) }
        response.post(callback)
    }
}
