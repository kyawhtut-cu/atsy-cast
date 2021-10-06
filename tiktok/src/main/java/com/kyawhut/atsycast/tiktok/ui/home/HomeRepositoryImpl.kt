package com.kyawhut.atsycast.tiktok.ui.home

import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.tiktok.data.network.TiktokAPI
import com.kyawhut.atsycast.tiktok.data.network.response.VideoResponse
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 10/4/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: TiktokAPI,
    private val crashlytics: Crashlytics,
) : HomeRepository {

    override suspend fun getVideoList(
        routeName: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getVideoList(scriptRequest {
                route = routeName
            }).videoList
        }
        response.post(callback)
    }
}
