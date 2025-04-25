package com.dahlaran.newmovshow.domain.repository

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow

/**
 * MediaRepository is an interface that can be used to get medias
 *
 * It can be implemented by a repository to get medias in a ViewModel
 */
interface MediaRepository {

    /**
     * Search a media by id
     *
     * @param id the id to search
     * @return a flow that will emit a DataState that contains a media
     */
    fun searchMediaById(id: String): Flow<DataState<Media?>>

    /**
     * Get favorite medias
     *
     * @return a flow that will emit a DataState that contains a list of favorite medias
     */
    fun getFavoriteMedias(): Flow<DataState<List<Media>>>

    /**
     * Add a media to favorite
     *
     * @param mediaId the media id to add to favorite
     */
    fun addFavoriteMedia(mediaId: String): Flow<DataState<Media>>

    /**
     * Remove a media from favorite
     *
     * @param mediaId the media id to remove from favorite
     */
    fun removeFavoriteMedia(mediaId: String): Flow<DataState<Media>>
}