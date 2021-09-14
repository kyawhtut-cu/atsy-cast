package com.kyawhut.atsycast.msubpc.ui.search

import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface SearchRepository {

    suspend fun search(
        query: String,
        apiKey: String,
        callback: (NetworkResponse<List<Pair<String, List<VideoResponse>>>>) -> Unit
    )
}