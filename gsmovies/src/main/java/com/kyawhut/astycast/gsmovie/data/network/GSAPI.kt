package com.kyawhut.astycast.gsmovie.data.network

import com.kyawhut.astycast.gsmovie.data.network.response.CategoryResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.request.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal interface GSAPI {

    @POST("exec")
    suspend fun getCategory(
        @Body request: ScriptRequest,
    ): CategoryResponse

    @POST("exec")
    suspend fun getVideoListByCategoryID(
        @Body request: ScriptRequest,
    ): VideoResponse

    @POST("exec")
    suspend fun getVideoDetail(
        @Body request: ScriptRequest,
    ): VideoDetailResponse

    @POST("exec")
    suspend fun search(
        @Body request: ScriptRequest,
    ): VideoResponse
}
