package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaUseCase @Inject constructor(private val mediaRepository: MediaRepository) {

    /**
     * Get specific media from the repository
     *
     * @param mediaId the id of the media to get
     * @return a flow of DataState<Media>
     */
    fun invoke(mediaId: String): Flow<DataState<Media?>> {
        return mediaRepository.searchMediaById(mediaId)
    }
}