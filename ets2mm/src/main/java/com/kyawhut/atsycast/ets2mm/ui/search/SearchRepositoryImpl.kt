package com.kyawhut.atsycast.ets2mm.ui.search

import android.content.Context
import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: Et2API,
    private val crashlytics: Crashlytics,
) : SearchRepository {

    override suspend fun search(
        context: Context,
        query: String,
        callback: (NetworkResponse<SearchResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.search(query) }
        if (response.isSuccess) {
            NetworkResponse.success(
                SearchResponse(
                    response.data?.moviesList?.filter {
                        !it.videoTitle.isAdult || context.isAdultOpen
                    } ?: listOf(),
                    response.data?.seriesList?.filter {
                        !it.videoTitle.isAdult || context.isAdultOpen
                    } ?: listOf()
                ),
                callback
            )
        } else response.post(callback)
        response.post(callback)
    }
}
