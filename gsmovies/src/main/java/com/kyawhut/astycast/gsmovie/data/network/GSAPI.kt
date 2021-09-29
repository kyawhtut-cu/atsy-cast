package com.kyawhut.astycast.gsmovie.data.network

import com.kyawhut.astycast.gsmovie.data.network.response.CategoryResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal interface GSAPI {

    @GET("exec")
    suspend fun getCategory(
        @Query("method") method: String,
        @Query("sub_route") subRoute: String = "category",
    ): CategoryResponse

    @GET("exec")
    suspend fun getVideoListByCategoryID(
        @Query("method") method: String,
        @Query("category_id") categoryID: Int,
        @Query("page") page: Int,
        @Query("sub_route") subRoute: String = "videoListByCategoryID",
    ): VideoResponse

    @GET("exec")
    suspend fun getVideoDetail(
        @Query("method") method: String,
        @Query("video_id") videoID: String,
        @Query("sub_route") subRoute: String = "videoDetail",
    ): VideoDetailResponse

    @GET("exec")
    suspend fun search(
        @Query("method") method: String,
        @Query("query") query: String,
        @Query("sub_route") subRoute: String = "search",
    ): VideoResponse
}
