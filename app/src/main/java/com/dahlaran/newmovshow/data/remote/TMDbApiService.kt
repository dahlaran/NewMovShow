package com.dahlaran.newmovshow.data.remote

import com.dahlaran.newmovshow.BuildConfig
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovie
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovieResponse
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

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") title: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): Response<TMDbMovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = "videos"
    ): Response<TMDbMovie>
}