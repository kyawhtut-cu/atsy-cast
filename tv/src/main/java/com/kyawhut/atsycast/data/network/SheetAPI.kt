package com.kyawhut.atsycast.data.network

import com.kyawhut.atsycast.data.network.response.FeatureDetailResponse
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.data.network.response.UserResponse
import com.kyawhut.atsycast.share.network.request.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface SheetAPI {

    @POST("exec")
    suspend fun getHomeFeature(
        @Body scriptRequest: ScriptRequest,
    ): HomeFeatureResponse

    @POST("exec")
    suspend fun getFeatureDetail(
        @Body scriptRequest: ScriptRequest,
    ): FeatureDetailResponse

    @POST("exec")
    suspend fun registerDevice(
        @Body scriptRequest: ScriptRequest,
    ): UserResponse

    @POST("exec")
    suspend fun checkDevicePassword(
        @Body scriptRequest: ScriptRequest,
    ): UserResponse

    @POST("exec")
    suspend fun checkAdultPassword(
        @Body scriptRequest: ScriptRequest,
    ): UserResponse

    @POST("exec")
    suspend fun changeDisplayName(
        @Body scriptRequest: ScriptRequest,
    ): UserResponse

    @POST("exec")
    suspend fun checkUpdate(
        @Body scriptRequest: ScriptRequest,
    ): UpdateResponse
}
