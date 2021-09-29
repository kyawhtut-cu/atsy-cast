package com.kyawhut.astycast.gsmovie.ui.detail

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface DetailRepository {

    suspend fun getVideoDetail(
        context: Context,
        route: String,
        videoID: String,
        callback: (NetworkResponse<VideoDetailResponse.Data>) -> Unit
    )
}
