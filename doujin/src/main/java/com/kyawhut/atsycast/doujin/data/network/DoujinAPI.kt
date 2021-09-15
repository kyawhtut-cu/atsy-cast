package com.kyawhut.atsycast.doujin.data.network

import androidx.annotation.Keep
import com.kyawhut.atsycast.doujin.data.network.request.DoujinSearchRequest
import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@Keep
internal interface DoujinAPI {

    @GET("videos/hot")
    suspend fun getHotData(): List<DoujinVideoResponse>

    @GET("videos/{PAGE_KEY}/page/{PAGE}")
    suspend fun getDataWithPage(
        @Path("PAGE_KEY") key: String,
        @Path("PAGE") page: Int
    ): DoujinPageVideoResponse

    @GET("video/{DOUJIN_ID}/user/getVideoDetails")
    suspend fun getVideoDetail(@Path("DOUJIN_ID") doujinID: String): DoujinVideoDetailResponse

    @POST("videos/search")
    suspend fun searchVideo(
        @Body data: DoujinSearchRequest
    ): DoujinPageVideoResponse
}
