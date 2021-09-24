package com.kyawhut.atsycast.data.network

import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.data.network.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 8/31/21
 */
interface SheetAPI {

    @GET("exec")
    suspend fun getHomeFeature(
        @Query("device_id") deviceID: String,
        @Query("device_password") password: String,
        @Query("method") method: String = "homeFeatures",
    ): HomeFeatureResponse

    @GET("exec")
    suspend fun registerDevice(
        @Query("device_id") deviceID: String,
        @Query("device_name") deviceName: String,
        @Query("method") method: String = "registerDevice",
    ): UserResponse

    @GET("exec")
    suspend fun checkDevicePassword(
        @Query("device_id") deviceID: String,
        @Query("device_password") password: String,
        @Query("method") method: String = "checkDevicePassword",
    ): UserResponse

    @GET("exec")
    suspend fun checkAdultPassword(
        @Query("device_id") deviceID: String,
        @Query("adult_password") password: String,
        @Query("method") method: String = "checkAdultPassword",
    ): UserResponse

    @GET("exec")
    suspend fun changeDisplayName(
        @Query("device_id") deviceID: String,
        @Query("display_name") displayName: String,
        @Query("method") method: String = "changeDisplayName",
    ): UserResponse

    @GET("exec")
    suspend fun checkUpdate(
        @Query("method") method: String = "checkUpdate",
    ): UpdateResponse
}
