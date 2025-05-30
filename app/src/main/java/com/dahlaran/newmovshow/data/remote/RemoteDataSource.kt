package com.dahlaran.newmovshow.data.remote

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media

interface RemoteDataSource {
    /**
     * Get all shows from API
     *
     * @param page Page of the shows
     */
    suspend fun getMediaShows(page: Int): DataState<List<Media>>

    /**
     * Get a show from API
     *
     * @param id Id of the show
     */
    suspend fun searchMediaById(id: String): DataState<Media>

    /**
     * Search a show by title from API
     *
     * @param title Title of the show
     */
    suspend fun searchMediaByTitle(title: String): DataState<List<Media>>
}