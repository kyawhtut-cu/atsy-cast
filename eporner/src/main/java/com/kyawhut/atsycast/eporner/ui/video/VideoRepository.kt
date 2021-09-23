package com.kyawhut.atsycast.eporner.ui.video

import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal interface VideoRepository {

    suspend fun getVideoData(
        pageKey: String,
        page: Int,
        callback: (NetworkResponse<List<EPornerVideoResponse>>) -> Unit
    )
}
