package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaUseCase @Inject constructor(
    private val remoteData: TVMazeApiServices,
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
                response.body()?.toMedia()?.let { remoteMedia ->
                    localMedia.data?.let { media ->
                        remoteMedia.isFavorite = media.isFavorite
                    }
                    localData.saveMedia(remoteMedia)
                    emit(DataState.Success(remoteMedia))
                } ?: run {
                    emit(DataState.Error(Error.fromResponseBody(response.errorBody())))
                }
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}