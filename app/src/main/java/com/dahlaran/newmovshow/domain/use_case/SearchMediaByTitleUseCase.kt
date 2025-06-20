package com.dahlaran.newmovshow.domain.use_case

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.domain.model.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * SearchMediaByTitle is a use case that can be used to search medias by title
 *
 * @property remoteData the remote data source to use
 * @property localData the local data source to use
 */
class SearchMediaByTitleUseCase @Inject constructor(
    private val remoteData: TVMazeApiServices,
    private val localData: MediaDatabase
) {

    /**
     * Search medias by title
     *
     * @param mediaTitle the title of the media to search
     * @param page the page to get
     * @return a flow of DataState<List<Media>>
     */
    fun invoke(mediaTitle: String, page: Int): Flow<DataState<List<Media>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                // TODO: add page to the search
                val savedData: List<Media> = localData.getMedias()
                val response = remoteData.searchMediaByTitle(mediaTitle)

                if (response.body() != null) {
                    response.body()?.map { item -> item.toMedia() }?.let { newListFromRemote ->
                        newListFromRemote.forEach { item ->
                            val tmp = savedData.find { item.id == it.id }
                            if (tmp != null) {
                                item.isFavorite = tmp.isFavorite
                            }
                        }
                        emit(DataState.Success(newListFromRemote))
                    } ?: run {
                        emit(DataState.Error(Error.fromResponseBody(response.errorBody())))
                    }
                } else {
                    emit(DataState.Error(Error.fromResponseBody(response.errorBody())))
                }
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}