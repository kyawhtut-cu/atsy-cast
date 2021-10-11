package com.kyawhut.atsycast.gs_mm.data.network

import com.kyawhut.atsycast.gs_mm.data.network.response.*
import com.kyawhut.atsycast.share.network.request.ScriptRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal interface GSMMAPI {

    @POST("exec")
    suspend fun getDrawerMenu(
        @Body request: ScriptRequest,
    ): DrawerResponse

    @POST("exec")
    suspend fun getDrawerDetail(
        @Body request: ScriptRequest,
    ): DrawerDetailResponse

    @POST("exec")
    suspend fun getVideoListByID(
        @Body request: ScriptRequest,
    ): VideoListByCategoryResponse

    @POST("exec")
    suspend fun getEpisodeList(
        @Body request: ScriptRequest,
    ): EpisodeResponse

    @POST("exec")
    suspend fun getRelatedVideoList(
        @Body request: ScriptRequest,
    ): RelatedResponse

    @POST("exec")
    suspend fun getPlaylistList(
        @Body request: ScriptRequest,
    ): RelatedResponse

    @POST("exec")
    suspend fun search(
        @Body request: ScriptRequest,
    ): SearchResponse
}
