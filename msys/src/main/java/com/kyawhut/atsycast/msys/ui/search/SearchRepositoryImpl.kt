package com.kyawhut.atsycast.msys.ui.search

import com.kyawhut.atsycast.msys.data.network.MsysAPI
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: MsysAPI
) : SearchRepository {

    override suspend fun search(
        query: String,
        apiKey: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.search(query, apiKey) }
        if (response.isSuccess) {
            NetworkResponse.success(response.data?.moviesList ?: listOf(), callback)
        } else NetworkResponse.error(response.error, callback)
    }
}
