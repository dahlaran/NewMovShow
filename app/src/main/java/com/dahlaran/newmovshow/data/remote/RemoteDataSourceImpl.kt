package com.dahlaran.newmovshow.data.remote

import com.dahlaran.newmovshow.BuildConfig
import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.common.data.ErrorCode
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovie
import com.dahlaran.newmovshow.data.remote.data.tmdb.TMDbMovieResponse
import com.dahlaran.newmovshow.data.remote.data.tvmaze.TVMazeShow
import com.dahlaran.newmovshow.domain.model.DataSource
import com.dahlaran.newmovshow.domain.model.DataSourceConfig
import com.dahlaran.newmovshow.domain.model.Media

class RemoteDataSourceImpl(
    private val tvMazeApiService: TVMazeApiService,
    private val tmDbApiService: TMDbApiService
) : RemoteDataSource {

    override suspend fun getMediaShows(page: Int): DataState<List<Media>> {
        return try {
            val response = when (DataSourceConfig.currentSource) {
                DataSource.TMDB -> tmDbApiService.getPopularMovies(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    page = page
                )

                DataSource.TVMAZE -> tvMazeApiService.getMediaShows(page = page)
            }
            if (response.isSuccessful) {
                val responseBody = response.body()

                if (responseBody is TMDbMovieResponse) {
                    val mediaList = (responseBody.results).map { it.toMedia() }
                    DataState.Success(mediaList)
                } else if (responseBody is List<*> && responseBody.isNotEmpty() && responseBody[0] is TVMazeShow) {
                    val mediaList = (responseBody as List<TVMazeShow>).map { it.toMedia() }
                    DataState.Success(mediaList)
                } else {
                    DataState.Error(Error(ErrorCode.CODE_NO_CONTENT))
                }
            } else {
                DataState.Error(Error.fromResponseBody(response.errorBody()))
            }
        } catch (e: Exception) {
            DataState.Error(Error.fromException(e))
        }
    }

    override suspend fun searchMediaById(id: String): DataState<Media> {
        return try {
            val response = when (DataSourceConfig.currentSource) {
                DataSource.TMDB -> tmDbApiService.getMovieDetails(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    id = id
                )

                DataSource.TVMAZE -> tvMazeApiService.searchMediaById(id = id)
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody is TMDbMovie) {
                    DataState.Success(responseBody.toMedia())
                } else if (responseBody is TVMazeShow) {
                    DataState.Success(responseBody.toMedia())
                } else {
                    DataState.Error(Error(ErrorCode.CODE_NO_CONTENT))
                }
            } else {
                DataState.Error(Error.fromResponseBody(response.errorBody()))
            }
        } catch (e: Exception) {
            DataState.Error(Error.fromException(e))
        }
    }

    override suspend fun searchMediaByTitle(title: String): DataState<List<Media>> {
        return try {
            val response = when (DataSourceConfig.currentSource) {
                DataSource.TMDB -> tmDbApiService.searchMovie(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    title = title
                )

                DataSource.TVMAZE -> tvMazeApiService.searchMediaByTitle(title = title)
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody is TMDbMovieResponse) {
                    val mediaList = (responseBody.results).map { it.toMedia() }
                    DataState.Success(mediaList)
                } else if (responseBody is List<*> && responseBody.isNotEmpty() && responseBody[0] is TVMazeShow) {
                    val mediaList = (responseBody as List<TVMazeShow>).map { it.toMedia() }
                    DataState.Success(mediaList)
                } else {
                    DataState.Error(Error(ErrorCode.CODE_NO_CONTENT))
                }
            } else {
                DataState.Error(Error.fromResponseBody(response.errorBody()))
            }
        } catch (e: Exception) {
            DataState.Error(Error.fromException(e))
        }
    }
}