package com.kyawhut.atsycast.doujin.ui.video

import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal interface VideoRepository {

    suspend fun getVideoData(
        pageKey: String,
        appVersion: String,
        packageName: String,
        page: Int,
        callback: (NetworkResponse<List<DoujinVideoResponse>>) -> Unit
    )

    suspend fun getVideoDetail(
        appVersion: String,
        packageName: String,
        doujinID: String,
        callback: (NetworkResponse<DoujinVideoDetailResponse>) -> Unit
    )
}
