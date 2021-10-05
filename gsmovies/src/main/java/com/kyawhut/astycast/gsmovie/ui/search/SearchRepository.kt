package com.kyawhut.astycast.gsmovie.ui.search

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
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
        callback: (NetworkResponse<List<VideoResponse.Data>>) -> Unit
    )
}