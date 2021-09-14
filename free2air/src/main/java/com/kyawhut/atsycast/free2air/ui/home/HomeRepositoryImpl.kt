package com.kyawhut.atsycast.free2air.ui.home

import com.kyawhut.atsycast.free2air.data.network.Free2AirAPI
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
class HomeRepositoryImpl @Inject constructor(private val api: Free2AirAPI) : HomeRepository {

    override suspend fun getFree2Air(
        key: String,
        callback: (NetworkResponse<HashMap<String, List<Free2AirResponse>>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        if (key.split(",").size > 1) {
            execute {
                api.getMultiFree2Air(key)
            }.post(callback)
        } else {
            val response = execute { api.getFree2Air(key) }
            if (response.isSuccess) {
                NetworkResponse.success(
                    hashMapOf(
                        "Channels" to response.data!!
                    ),
                    callback
                )
            } else {
                NetworkResponse.error(response.error, callback)
            }
        }
    }
}
