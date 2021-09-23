package com.kyawhut.atsycast.eporner.data.network

import com.kyawhut.atsycast.eporner.data.network.response.EPornerResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 9/22/21
 */
internal interface EPornerAPI {

    @GET("search/")
    suspend fun getVideos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("thumbsize") thumbSize: String = "big",
        @Query("order") order: String = "latest",
        @Query("gay") gay: Int = 1,
        @Query("lq") q: Int = 1,
        @Query("format") format: String = "json"
    ): EPornerResponse
}
