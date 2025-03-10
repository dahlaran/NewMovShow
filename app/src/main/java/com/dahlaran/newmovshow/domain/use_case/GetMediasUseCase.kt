package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetMediasUseCase is a use case that can be used to get a list of media
 */
class GetMediasUseCase @Inject constructor(private val mediaRepository: MediaRepository) {

    /**
     * Get medias from the repository
     *
     * @param page the page to get
     * @return a flow of DataState<List<Media>>
     */
    fun invoke(page: Int): Flow<DataState<List<Media>>> {
        return mediaRepository.getMedias(page)
    }
}