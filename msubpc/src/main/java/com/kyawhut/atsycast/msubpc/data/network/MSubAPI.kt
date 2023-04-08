package com.kyawhut.atsycast.msubpc.data.network

import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MovieStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msubpc.data.network.response.SearchResponse
import com.kyawhut.atsycast.msubpc.data.network.response.SeriesResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author kyawhtut
 * @date 9/2/21
 */
internal interface MSubAPI {

    @GET("v2/fortv/movie/{movieID}")
    suspend fun getMovieStreamLink(
        @Path("movieID") movieID: String
    ): MovieStreamResponse

    @GET("v2/fortv/all")
    suspend fun getAllMovies(): MoviesResponse

    @GET("v2/fortv/movie/{movieID}")
    suspend fun getMovieStream(
        @Path("movieID") moviesID: String
    ): MovieStreamResponse

    @GET("v2/fortv/all")
    suspend fun getAllSeries(): SeriesResponse

    @GET("v2/fortv/episode/{SERIES_ID}")
    suspend fun getSeriesEpisode(
        @Path("SERIES_ID") seriesID: Int
    ): List<EpisodeResponse>

    @GET("encrypt/season/{SERIES_ID}")
    suspend fun getRelatedSeason(
        @Path("SERIES_ID") seriesID: Int
    ): List<VideoResponse>

    @GET("related/{genres}")
    suspend fun getRelatedMovies(
        @Path("genres") genres: String
    ): List<VideoResponse>

    @GET("v2/fortv/all")
    suspend fun search(): SearchResponse

    @GET("v2/fortv/football")
    suspend fun getFootball(): List<FootballResponse>

    @GET("v2/fortv/flive/{FOOTBALL_ID}")
    suspend fun getFootballStream(
        @Path("FOOTBALL_ID") footballID: Int
    ): List<FootballStreamResponse>

    @GET("v2/get/adult")
    suspend fun getAdultMovie(): List<AdultResponse>
}
