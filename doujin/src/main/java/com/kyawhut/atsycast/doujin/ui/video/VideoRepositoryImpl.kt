package com.kyawhut.atsycast.doujin.ui.video

import com.kyawhut.atsycast.doujin.data.network.DoujinAPI
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal class VideoRepositoryImpl @Inject constructor(
    private val api: DoujinAPI
) : VideoRepository {

    override suspend fun getVideoData(
        pageKey: String,
        page: Int,
        callback: (NetworkResponse<List<DoujinVideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute {
            when (page) {
                -1 -> api.getHotData()
                else -> api.getDataWithPage(pageKey, page).data
            }
        }
        response.post(callback)
    }

    override suspend fun getVideoDetail(
        doujinID: String,
        callback: (NetworkResponse<DoujinVideoDetailResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getVideoDetail(doujinID) }
        response.post(callback)
    }
}
