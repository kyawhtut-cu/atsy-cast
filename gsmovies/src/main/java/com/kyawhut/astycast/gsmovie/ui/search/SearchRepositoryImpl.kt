package com.kyawhut.astycast.gsmovie.ui.search

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: GSAPI,
    private val crashlytics: Crashlytics,
) : SearchRepository {

    override suspend fun search(
        context: Context,
        route: String,
        query: String,
        callback: (NetworkResponse<List<VideoResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.search(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "query" to query,
                    "sub_route" to "search"
                )
            })
        }
        if (response.isSuccess) {
            NetworkResponse.success(response.data?.data ?: listOf(), callback)
        } else NetworkResponse.error(response.error, callback)
    }
}
