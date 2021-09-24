package com.kyawhut.atsycast.free2air.ui.home

import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 8/31/21
 */
internal interface HomeRepository {

    suspend fun getFree2Air(
        key: String,
        callback: (NetworkResponse<HashMap<String, List<Free2AirResponse.Data>>>) -> Unit
    )
}
