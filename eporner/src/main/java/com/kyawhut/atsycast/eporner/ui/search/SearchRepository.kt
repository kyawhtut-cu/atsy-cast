package com.kyawhut.atsycast.eporner.ui.search

import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        query: String,
        page: Int,
        callback: (NetworkResponse<List<EPornerVideoResponse>>) -> Unit
    )

}
