package com.kyawhut.atsycast.doujin.data.network

import androidx.annotation.Keep
import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal interface DoujinAPI {

    @GET("videos/hot")
    suspend fun getHotData(
        @Header("App-Package") packageName: String,
        @Header("App-Version") appVersion: String,
    ): List<DoujinVideoResponse>

    @GET("videos/{PAGE_KEY}/page/{PAGE}")
    suspend fun getDataWithPage(
        @Header("App-Package") packageName: String,
        @Header("App-Version") appVersion: String,
        @Path("PAGE_KEY") key: String,
        @Path("PAGE") page: Int
    ): DoujinPageVideoResponse

    @GET("video/{DOUJIN_ID}/user/getVideoDetails")
    suspend fun getVideoDetail(
        @Header("App-Package") packageName: String,
        @Header("App-Version") appVersion: String,
        @Path("DOUJIN_ID") doujinID: String,
    ): DoujinVideoDetailResponse
}
