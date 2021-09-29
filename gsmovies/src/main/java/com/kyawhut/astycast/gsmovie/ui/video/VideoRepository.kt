package com.kyawhut.astycast.gsmovie.ui.video

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface VideoRepository {

    suspend fun getVideoList(
        context: Context,
        categoryID: Int,
        route: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse.Data>>) -> Unit
    )
}
