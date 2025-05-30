package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.RemoteDataSource
import com.dahlaran.newmovshow.data.remote.TVMazeApiService
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaUseCase @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: MediaDatabase
) {

    /**
     * Get specific media from local and remote source
     *
     * @param mediaId the id of the media to get
     * @return a flow of DataState<Media>
     */
    fun invoke(mediaId: String): Flow<DataState<Media?>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val localMedia = DataState.Success(localData.getMediaById(mediaId))
                emit(localMedia)

                val response = remoteData.searchMediaById(mediaId)
                if (response is DataState.Success) {
                    response.data.isFavorite = localMedia.data?.isFavorite == true
                }
                emit(response)
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}