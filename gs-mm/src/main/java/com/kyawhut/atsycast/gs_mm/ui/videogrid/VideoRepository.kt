package com.kyawhut.atsycast.gs_mm.ui.videogrid

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface VideoRepository {

    suspend fun getVideoList(
        context: Context,
        categoryID: String,
        route: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
