package com.kyawhut.atsycast.free2air.data.network

import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.share.network.request.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kyawhtut
 * @date 8/31/21
 */
internal interface Free2AirAPI {

    @POST("exec")
    suspend fun getFree2Air(
        @Body scriptRequest: ScriptRequest,
    ): Free2AirResponse

}
