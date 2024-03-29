package com.kyawhut.atsycast.ets2mm.ui.search

import android.content.Context
import com.kyawhut.atsycast.ets2mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        context: Context,
        query: String,
        callback: (NetworkResponse<SearchResponse>) -> Unit
    )
}