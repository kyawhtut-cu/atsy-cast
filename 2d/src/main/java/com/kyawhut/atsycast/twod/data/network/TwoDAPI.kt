package com.kyawhut.atsycast.twod.data.network

import com.kyawhut.atsycast.twod.data.network.response.LuckyNumberResponse
import com.kyawhut.atsycast.twod.data.network.response.ThreeDHistoryResponse
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse
import com.kyawhut.atsycast.twod.data.network.response.TwoDHistoryResponse
import retrofit2.http.GET

/**
 * @author kyawhtut
 * @date 9/20/21
 */
interface TwoDAPI {

    @GET("home")
    suspend fun getToday(): TodayResponse

    @GET("lucky-number")
    suspend fun getLuckyNumber(): LuckyNumberResponse

    @GET("2d")
    suspend fun getTwoDHistory(): TwoDHistoryResponse

    @GET("3d")
    suspend fun getThreeDHistory(): ThreeDHistoryResponse
}
