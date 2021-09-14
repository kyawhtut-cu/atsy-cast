package com.kyawhut.atsycast.msys.data.network

import com.kyawhut.atsycast.msys.data.network.response.GenresResponse
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.data.network.response.SearchResponse
import com.kyawhut.atsycast.msys.data.network.response.SeasonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface MsysAPI {

    @GET("genre/all/{API_KEY}/")
    suspend fun getHome(
        @Path("API_KEY") apiKey: String
    ): List<GenresResponse>

    @GET("poster/by/filtres/{GENRES_ID}/created/{PAGE}/{API_KEY}/")
    suspend fun getMovies(
        @Path("GENRES_ID") genresID: Int,
        @Path("API_KEY") apiKey: String,
        @Path("PAGE") page: Int = 0,
    ): List<MoviesResponse>

    @GET("movie/random/{GENRES_ID}/{API_KEY}/")
    suspend fun getRelated(
        @Path("GENRES_ID") genresID: String,
        @Path("API_KEY") apiKey: String,
    ): List<MoviesResponse>

    @GET("season/by/serie/{SERIES_ID}/{API_KEY}/")
    suspend fun getSeriesSeason(
        @Path("SERIES_ID") seriesID: Int,
        @Path("API_KEY") apiKey: String,
    ): List<SeasonResponse>

    @GET("search/{QUERY}/{API_KEY}/")
    suspend fun search(
        @Path("QUERY") query: String,
        @Path("API_KEY") apiKey: String,
    ): SearchResponse

    @GET
    suspend fun getRedirectURL(
        @Url url: String
    ): retrofit2.Response<Any>

}
