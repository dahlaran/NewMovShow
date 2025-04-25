package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * AddFavoriteMediaUseCase is a use case that can be used to add a media to favorite
 *
 * @property localData the local data source to use
 */
class AddFavoriteMediaUseCase @Inject constructor(private val localData: MediaDatabase) {
    fun invoke(mediaId: String): Flow<DataState<Media>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                localData.addFavoriteMedia(mediaId)?.let {
                    emit(DataState.Success(it))
                } ?: run {
                    emit(DataState.Error(Error.fromException(Exception("Failed to add favorite media"))))
                }
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}