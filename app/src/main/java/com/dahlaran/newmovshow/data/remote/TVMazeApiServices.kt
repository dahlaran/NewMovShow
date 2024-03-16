package com.dahlaran.newmovshow.data.remote

import com.dahlaran.newmovshow.data.remote.data.TVMazeMedia
import com.dahlaran.newmovshow.data.remote.data.TVMazeShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface of TVMaze API
 */
interface TVMazeApiServices {

    /**
     * Get all shows from TVMaze API
     *
     * @param page Page of the shows
     */
    @GET("shows")
    suspend fun getMediaShows(@Query("page") page: Int): Response<List<TVMazeShow>?>

    /**
     * Get a show from TVMaze
     *
     * @param id Id of the show
     */
    @GET("shows/{id}?embed=episodes")
    suspend fun searchMediaById(@Path("id") id: String): Response<TVMazeShow>

    /**
     * Search a show by title
     *
     * @param title Title of the show
     */
    @GET("search/shows?embed=episodes")
    suspend fun searchMediaByTitle(@Query("q") title: String): Response<List<TVMazeMedia>?>
}