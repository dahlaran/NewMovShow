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
     * Get a list of medias
     *
     * @param page the page to get
     * @return a flow that will emit a DataState that contains a list of medias
     */
    fun getMedias(page: Int): Flow<DataState<List<Media>>>

    /**
     * Search a list of media by title
     *
     * @param title the title to search
     * @return a flow that will emit a DataState that contains a list of medias
     */
    fun searchMediaByTitle(title: String): Flow<DataState<List<Media>>>

    /**
     * Search a media by id
     *
     * @param id the id to search
     * @return a flow that will emit a DataState that contains a media
     */
    fun searchMediaById(id: String): Flow<DataState<Media?>>
}