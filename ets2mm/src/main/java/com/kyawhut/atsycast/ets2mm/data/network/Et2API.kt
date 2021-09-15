package com.kyawhut.atsycast.ets2mm.data.network

import com.kyawhut.atsycast.ets2mm.data.network.response.GenresResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kyawhtut
 * @date 9/15/21
 */
internal interface Et2API {

    @GET("all_genre")
    suspend fun getHome(): List<GenresResponse>

    @GET("movies")
    suspend fun getLatestMovies(
        @Query("page") page: Int,
    ): List<VideoResponse>

    @GET("tvseries")
    suspend fun getLatestTvSeries(
        @Query("page") page: Int,
    ): List<VideoResponse>

    @GET("content_by_genre_id")
    suspend fun getVideoByGenresID(
        @Query("id") genresID: String,
        @Query("page") page: Int,
    ): List<VideoResponse>

    @GET("single_details")
    suspend fun getVideoDetail(
        @Query("type") type: String,
        @Query("id") videoID: Int,
    ): VideoDetailResponse

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "movieserieslive",
    ): SearchResponse
}
