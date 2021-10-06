package com.kyawhut.atsycast.eporner.ui.search

import com.kyawhut.atsycast.eporner.data.network.EPornerAPI
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: EPornerAPI,
    private val crashlytics: Crashlytics,
) : SearchRepository {

    override suspend fun search(
        query: String,
        page: Int,
        callback: (NetworkResponse<List<EPornerVideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getVideos(query, page).videoList }
        response.post(callback)
    }
}
