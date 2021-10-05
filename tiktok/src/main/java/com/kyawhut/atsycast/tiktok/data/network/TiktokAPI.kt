package com.kyawhut.atsycast.tiktok.data.network

import com.kyawhut.atsycast.share.network.request.ScriptRequest
import com.kyawhut.atsycast.tiktok.data.network.response.VideoListResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author kyawhtut
 * @date 10/4/21
 */
internal interface TiktokAPI {

    @POST("exec")
    suspend fun getVideoList(
        @Body request: ScriptRequest
    ): VideoListResponse
}
