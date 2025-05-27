package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.data.remote.data.TVMazeMedia
import com.dahlaran.newmovshow.data.remote.data.TVMazeRating
import com.dahlaran.newmovshow.data.remote.data.TVMazeShow
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.SearchMediaByTitleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchMediaByTitleUseCaseTest {

    private lateinit var mediaDatabase: MediaDatabase
    private lateinit var apiService: TVMazeApiServices
    private lateinit var useCase: SearchMediaByTitleUseCase

    @Before
    fun setup() {
        mediaDatabase = mockk()
        apiService = mockk()
        useCase = SearchMediaByTitleUseCase(apiService, mediaDatabase)
    }

    @Test
    fun `invoke returns search results with preserved favorite status`() = runBlocking {
        val title = "Breaking Bad"
        val page = 0
        val savedMedia = listOf(createTestMedia("1", true))
        val searchResults = listOf(
            TVMazeMedia(0.95, createTestTVMazeShow(1)),
            TVMazeMedia(0.87, createTestTVMazeShow(2))
        )

        coEvery { mediaDatabase.getMedias() } returns savedMedia
        coEvery { apiService.searchMediaByTitle(title) } returns Response.success(searchResults)

        val results = useCase.invoke(title, page).toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)

        val successResult = results[1] as DataState.Success
        assert(successResult.data.size == 2)
        assert(successResult.data.first { it.id == "1" }.isFavorite) // Preserved favorite status
        assert(!successResult.data.first { it.id == "2" }.isFavorite)

        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
    }

    @Test
    fun `invoke handles empty search results`() = runBlocking {
        val title = "NonExistentShow"
        coEvery { mediaDatabase.getMedias() } returns emptyList()
        coEvery { apiService.searchMediaByTitle(title) } returns Response.success(emptyList())

        val results = useCase.invoke(title, 0).toList()

        val successResult = results.find { it is DataState.Success } as DataState.Success
        assert(successResult.data.isEmpty())
    }

    @Test
    fun `invoke handles API error`() = runBlocking {
        val title = "TestShow"
        coEvery { mediaDatabase.getMedias() } returns emptyList()
        coEvery { apiService.searchMediaByTitle(title) } returns Response.error(500, "".toResponseBody())

        val results = useCase.invoke(title, 0).toList()

        assert(results.any { it is DataState.Error })
    }

    @Test
    fun `invoke handles network exception`() = runBlocking {
        val title = "TestShow"
        coEvery { mediaDatabase.getMedias() } returns emptyList()
        coEvery { apiService.searchMediaByTitle(title) } throws java.io.IOException("Network error")

        val results = useCase.invoke(title, 0).toList()

        assert(results.any { it is DataState.Error })
    }

    private fun createTestMedia(id: String, isFavorite: Boolean = false): Media {
        return Media(
            id = id,
            genres = listOf("Drama"),
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
            isFavorite = isFavorite
        )
    }

    private fun createTestTVMazeShow(id: Int): TVMazeShow {
        return TVMazeShow(
            id = id,
            genres = listOf("Drama"),
            image = null,
            language = "English",
            name = "Test Show $id",
            officialSite = "http://example.com",
            premiered = "2021-01-01",
            rating = TVMazeRating(8.5),
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
            schedule = null,
            webChannel = null
        )
    }
}
