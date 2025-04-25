package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritesMediaUseCase @Inject constructor(private val localData: MediaDatabase) {
    fun invoke(): Flow<DataState<List<Media>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                emit(DataState.Success(localData.getFavoriteMedias()))
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}