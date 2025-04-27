package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.ErrorCode
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.data.remote.data.TVMazeMedia
import com.dahlaran.newmovshow.data.remote.data.TVMazeRating
import com.dahlaran.newmovshow.data.remote.data.TVMazeSchedule
import com.dahlaran.newmovshow.data.remote.data.TVMazeShow
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetMediasUseCaseTest {
    lateinit var getMediasUseCase: GetMediasUseCase

    @MockK
    lateinit var localData: MediaDatabase

    @MockK
    lateinit var remoteData: TVMazeApiServices

    @Before
    fun before() {
        localData = mockk()
        remoteData = mockk()

        getMediasUseCase = GetMediasUseCase(
            localData = localData,
            remoteData = remoteData
        )
    }

    @Test
    fun `invoke emits loading and success states when operation succeeds`() = runBlocking {
        val expectedResultDatabase = listOf(
            createTestMedia("1", isFavorite = false),
            createTestMedia("2")
        )
        val expectedResultNetwork : List<TVMazeShow> = listOf(
            TVMazeShow(
                id = 1,
                genres = listOf("Drama", "Comedy"),
                image = null,
                language = "English",
                name = "Test Show 1",
                officialSite = "http://example.com",
                premiered = "2021-01-01",
                rating = TVMazeRating(
                    average = 8.5
                ),
                runtime = 60,
                status = "Running",
                summary = "Test summary",
                type = "scripted",
                updated = 12345678,
                url = "http://example.com/show",
                weight = 100,
                embedded = null,
                externals = null,
                network = null,
                schedule = TVMazeSchedule(
                    time = "20:00",
                    days = listOf("Monday")
                ),
                webChannel = null,
            ), TVMazeShow(
                id = 2,
                genres = listOf("Drama", "Comedy"),
                image = null,
                language = "English",
                name = "Test Show 1",
                officialSite = "http://example.com",
                premiered = "2021-01-01",
                rating = TVMazeRating(
                    average = 8.5
                ),
                runtime = 60,
                status = "Running",
                summary = "Test summary",
                type = "scripted",
                updated = 12345678,
                url = "http://example.com/show",
                weight = 100,
                embedded = null,
                externals = null,
                network = null,
                schedule = TVMazeSchedule(
                    time = "20:00",
                    days = listOf("Monday")
                ),
                webChannel = null,
            )
        )

        coEvery { remoteData.getMediaShows(0) } returns Response.success(expectedResultNetwork)
        coEvery { localData.getMedias() } returns expectedResultDatabase
        coEvery { localData.saveMedias(any()) } returns Unit
        val results = getMediasUseCase.invoke(0).toList()

        assert(results.size == 4)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == expectedResultDatabase)
        assert(results[2] is DataState.Success && (results[2] as DataState.Success).data == expectedResultNetwork.map { it.toMedia() })
        assert(results[3] is DataState.Loading && !(results[3] as DataState.Loading).isLoading)
    }

    @Test
    fun `invoke emits loading and error states when operation fails`() = runBlocking {
        coEvery { remoteData.getMediaShows(0) } throws java.io.IOException("Network error")
        coEvery { localData.getMedias() } returns emptyList()

        val results = getMediasUseCase.invoke(0).toList()

        assert(results.size == 4)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == emptyList<Media>())
        assert(results[2] is DataState.Error && (results[2] as DataState.Error).error.status == ErrorCode.CODE_NETWORK_PROBLEM)
        assert(results[3] is DataState.Loading && !(results[3] as DataState.Loading).isLoading)
    }

    private fun createTestMedia(id: String, isFavorite: Boolean = false): Media {
        return Media(
            id = id,
            genres = listOf("Drama", "Comedy"),
            image = "http://example.com/image.jpg",
            language = "English",
            title = "Test Show $id",
            officialSite = "http://example.com",
            premiered = "2021-01-01",
            rating = 8.5,
            runtime = 60,
            seasons = null,
            status = "Running",
            summary = "Test summary",
            type = "scripted",
            updated = 12345678,
            url = "http://example.com/show",
            weight = 100,
            isFavorite = isFavorite,
        )
    }
}