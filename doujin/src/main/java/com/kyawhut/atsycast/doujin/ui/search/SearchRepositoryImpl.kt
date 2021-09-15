package com.kyawhut.atsycast.doujin.ui.search

import com.kyawhut.atsycast.doujin.data.network.DoujinAPI
import com.kyawhut.atsycast.doujin.data.network.request.DoujinSearchRequest
import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: DoujinAPI
) : SearchRepository {

    override suspend fun search(
        query: String,
        page: Int,
        callback: (NetworkResponse<DoujinPageVideoResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute {
            api.searchVideo(DoujinSearchRequest(query, page))
        }
        response.post(callback)
    }

    override suspend fun getVideoDetail(
        doujinID: String,
        callback: (NetworkResponse<DoujinVideoDetailResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getVideoDetail(doujinID) }
        response.post(callback)
    }
}
