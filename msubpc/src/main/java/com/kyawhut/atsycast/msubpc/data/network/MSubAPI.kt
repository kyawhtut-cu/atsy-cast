package com.kyawhut.atsycast.msubpc.data.network

import com.kyawhut.atsycast.msubpc.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * @author kyawhtut
 * @date 9/2/21
 */
internal interface MSubAPI {

    /*MaAyeLoeNgarLoeMaThar:6d8b0d7d19b343a2844408a925b56e50*/

    @GET("encrypt/movie")
    suspend fun getAllMovies(
        @Header("baby") apiKey: String,
    ): List<VideoResponse>

    @GET("encrypt/series")
    suspend fun getAllSeries(
        @Header("baby") apiKey: String
    ): List<VideoResponse>

    @GET("encrypt/episode/{SERIES_ID}")
    suspend fun getSeriesEpisode(
        @Header("baby") apiKey: String,
        @Path("SERIES_ID") seriesID: Int
    ): List<EpisodeResponse>

    @GET("encrypt/season/{SERIES_ID}")
    suspend fun getRelatedSeason(
        @Header("baby") apiKey: String,
        @Path("SERIES_ID") seriesID: Int
    ): List<VideoResponse>

    @GET("movie/{MOVIES_ID}")
    suspend fun getMovieStream(
        @Header("baby") apiKey: String,
        @Path("MOVIES_ID") moviesID: String
    ): MovieStreamResponse

    @GET("related/{GENRES}")
    suspend fun getRelatedMovies(
        @Header("baby") apiKey: String,
        @Path("GENRES") genres: String
    ): List<VideoResponse>

    @GET("new/all")
    suspend fun search(
        @Header("baby") apiKey: String
    ): SearchResponse

    @GET("new/all")
    suspend fun getFootball(
        @Header("baby") apiKey: String
    ): FootballResponse

    @GET("football/showlive/{FOOTBALL_ID}")
    suspend fun getFootballStream(
        @Header("baby") apiKey: String,
        @Path("FOOTBALL_ID") footballID: Int
    ): FootballStreamResponse
}
