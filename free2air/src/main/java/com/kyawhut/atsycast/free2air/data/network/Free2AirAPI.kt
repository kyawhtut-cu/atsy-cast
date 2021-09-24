package com.kyawhut.atsycast.free2air.data.network

import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 8/31/21
 */
internal interface Free2AirAPI {

    @GET("exec")
    suspend fun getFree2Air(
        @Query("method") sheetNo: String = "",
    ): Free2AirResponse

}
