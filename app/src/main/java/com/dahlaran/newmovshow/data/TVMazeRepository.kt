package com.dahlaran.newmovshow.data

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * TVMazeRepository is a repository that can be used to get medias from TVMaze
 *
 * It implements MediaRepository
 */
class TVMazeRepository @Inject constructor(private val service: TVMazeApiServices, private val database: MediaDatabase):
    MediaRepository {
    override fun getMedias(page: Int): Flow<DataState<List<Media>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                if (page == 0) {
                    emit(DataState.Success(database.getMedias()))
                }

                val response = service.getMediaShows(page)

                if (response.body() != null) {
                    response.body()?.map { item -> item.toMedia() }?.let {
                        database.saveMedias(it)
                        emit(DataState.Success(it))
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

    override fun searchMediaByTitle(title: String, page: Int): Flow<DataState<List<Media>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                val response = service.searchMediaByTitle(title)

                if (response.body() != null) {
                    response.body()?.map { item -> item.toMedia() }?.let {
                        emit(DataState.Success(it))
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

    override fun searchMediaById(id: String): Flow<DataState<Media?>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                emit(DataState.Success(database.getMediaById(id)))

                val response = service.searchMediaById(id)
                response.body()?.toMedia()?.let {
                    emit(DataState.Success(it))
                } ?: run {
                    emit(DataState.Error(Error.fromResponseBody(response.errorBody())))
                }
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }

    override fun getFavoriteMedias(): Flow<DataState<List<Media>>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                emit(DataState.Success(database.getFavoriteMedias()))
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }

    override fun addFavoriteMedia(mediaId: String): Flow<DataState<Media>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                database.addFavoriteMedia(mediaId)?.let {
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

    override fun removeFavoriteMedia(mediaId: String): Flow<DataState<Media>> {
        return flow {
            emit(DataState.Loading(true))
            try {
                database.removeFavoriteMedia(mediaId)?.let {
                    emit(DataState.Success(it))
                } ?: run {
                    emit(DataState.Error(Error.fromException(Exception("Failed to remove favorite media"))))
                }
            } catch (e: Exception) {
                emit(DataState.Error(Error.fromException(e)))
            }
            emit(DataState.Loading(false))
        }
    }
}