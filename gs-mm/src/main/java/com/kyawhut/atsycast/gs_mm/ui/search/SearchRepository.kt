package com.kyawhut.atsycast.gs_mm.ui.search

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        context: Context,
        route: String,
        query: String,
        callback: (NetworkResponse<List<SearchResponse.Data>>) -> Unit
    )
}