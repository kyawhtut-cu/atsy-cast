package com.kyawhut.atsycast.data.network

import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface SheetAPI {

    @GET("exec")
    suspend fun getHomeFeature(
        @Query("sheet_no") sheetNo: Int = 2,
    ): List<HomeFeatureResponse>
}
