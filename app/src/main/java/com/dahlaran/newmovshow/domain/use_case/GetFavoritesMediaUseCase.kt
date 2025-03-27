package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesMediaUseCase @Inject constructor(private val mediaRepository: MediaRepository) {
    fun invoke(): Flow<DataState<List<Media>>> {
        return mediaRepository.getFavoriteMedias()
    }
}