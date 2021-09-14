package com.kyawhut.atsycast.free2air.data.network

import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface Free2AirAPI {

    @GET("exec")
    suspend fun getMultiFree2Air(
        @Query("sheet_no") sheetNo: String = "",
    ): HashMap<String, List<Free2AirResponse>>

    @GET("exec")
    suspend fun getFree2Air(
        @Query("sheet_no") sheetNo: String = "",
    ): List<Free2AirResponse>

}
