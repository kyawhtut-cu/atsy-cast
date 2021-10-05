package com.kyawhut.atsycast.tiktok.ui.home

import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.tiktok.data.network.response.VideoResponse

/**
 * @author kyawhtut
 * @date 10/4/21
 */
internal interface HomeRepository {

    suspend fun getVideoList(
        routeName: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
