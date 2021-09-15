package com.kyawhut.atsycast.doujin.ui.search

import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        query: String,
        page: Int,
        callback: (NetworkResponse<DoujinPageVideoResponse>) -> Unit
    )

    suspend fun getVideoDetail(
        doujinID: String,
        callback: (NetworkResponse<DoujinVideoDetailResponse>) -> Unit
    )
}