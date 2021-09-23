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
        @Query("password") password: String,
        @Query("sheet_no") sheetNo: Int = 2,
    ): List<HomeFeatureResponse>

    @GET("exec")
    suspend fun registerDevice(
        @Query("device_id") deviceID: String,
        @Query("device_name") deviceName: String,
        @Query("method") method: String = "registerDevice",
    ): UserResponse

    @GET("exec")
    suspend fun checkDevicePassword(
        @Query("device_id") deviceID: String,
        @Query("password") password: String,
        @Query("method") method: String = "checkDevice",
    ): UserResponse

    @GET("exec")
    suspend fun checkAdultPassword(
        @Query("device_id") deviceID: String,
        @Query("password") password: String,
        @Query("method") method: String = "checkPassword",
    ): UserResponse

    @GET("exec")
    suspend fun changeDisplayName(
        @Query("device_id") deviceID: String,
        @Query("display_name") displayName: String,
        @Query("method") method: String = "changeName",
    ): UserResponse

    @GET("exec")
    suspend fun checkUpdate(
        @Query("method") method: String = "checkUpdate",
    ): List<UpdateResponse>
}
