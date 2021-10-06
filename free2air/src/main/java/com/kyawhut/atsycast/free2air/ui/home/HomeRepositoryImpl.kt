package com.kyawhut.atsycast.free2air.ui.home

import com.kyawhut.atsycast.free2air.data.network.Free2AirAPI
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: Free2AirAPI,
    private val crashlytics: Crashlytics,
) : HomeRepository {

    override suspend fun getFree2Air(
        key: String,
        callback: (NetworkResponse<HashMap<String, List<Free2AirResponse.Data>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        execute(crashlytics) {
            api.getFree2Air(scriptRequest {
                route = key
            }).data
        }.post(callback)
    }
}
