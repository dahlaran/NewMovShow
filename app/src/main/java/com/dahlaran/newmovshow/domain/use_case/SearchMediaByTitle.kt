package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * SearchMediaByTitle is a use case that can be used to search medias by title
 */
class SearchMediaByTitle @Inject constructor(private val mediaRepository: MediaRepository) {

    /**
     * Search medias by title from the repository
     *
     * @param mediaTitle the title of the media to search
     * @param page the page to get
     * @return a flow of DataState<List<Media>>
     */
    fun invoke(mediaTitle: String, page: Int): Flow<DataState<List<Media>>> {
        return mediaRepository.searchMediaByTitle(mediaTitle, page)
    }
}