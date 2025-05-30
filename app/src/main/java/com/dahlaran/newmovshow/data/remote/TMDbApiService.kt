package com.dahlaran.newmovshow.data.remote

import com.dahlaran.newmovshow.BuildConfig
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovie
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovieResponse
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMultiSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TMDbMovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TMDbMovieResponse>

    @GET("search/multi")
    suspend fun searchMulti(
        @Query("api_key") apiKey: String,
        @Query("query") title: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TMDbMultiSearchResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<TMDbMovie>
}