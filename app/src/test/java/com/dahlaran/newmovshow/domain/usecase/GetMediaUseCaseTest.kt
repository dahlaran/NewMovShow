package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.data.remote.TVMazeApiServices
import com.dahlaran.newmovshow.data.remote.data.TVMazeRating
import com.dahlaran.newmovshow.data.remote.data.TVMazeShow
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.GetMediaUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GetMediaUseCaseTest {

    private lateinit var mediaDatabase: MediaDatabase
    private lateinit var apiService: TVMazeApiServices
    private lateinit var useCase: GetMediaUseCase

    @Before
    fun setup() {
        mediaDatabase = mockk()
        apiService = mockk()
        useCase = GetMediaUseCase(apiService, mediaDatabase)
    }

    @Test
    fun `invoke returns local data first then remote data when both succeed`() = runBlocking {
        val mediaId = "123"
        val localMedia = createTestMedia(mediaId, true)
        val remoteShow = createTestTVMazeShow(mediaId.toInt())
        val remoteMedia = remoteShow.toMedia().copy(isFavorite = true)

        coEvery { mediaDatabase.getMediaById(mediaId) } returns localMedia
        coEvery { apiService.searchMediaById(mediaId) } returns Response.success(remoteShow)
        coEvery { mediaDatabase.saveMedia(any()) } returns Unit

        val results = useCase.invoke(mediaId).toList()

        assert(results.size == 4)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == localMedia)
        assert(results[2] is DataState.Success && (results[2] as DataState.Success).data == remoteMedia)
        assert(results[3] is DataState.Loading && !(results[3] as DataState.Loading).isLoading)

        coVerify { mediaDatabase.saveMedia(remoteMedia) }
    }

    @Test
    fun `invoke returns local data when remote fails`() = runBlocking {
        val mediaId = "123"
        val localMedia = createTestMedia(mediaId)

        coEvery { mediaDatabase.getMediaById(mediaId) } returns localMedia
        coEvery { apiService.searchMediaById(mediaId) } returns Response.error(404, "".toResponseBody())

        val results = useCase.invoke(mediaId).toList()

        assert(results.any { it is DataState.Success && it.data == localMedia })
        assert(results.any { it is DataState.Error })
    }

    @Test
    fun `invoke preserves favorite status from local data`() = runBlocking {
        val mediaId = "123"
        val localMedia = createTestMedia(mediaId, true)
        val remoteShow = createTestTVMazeShow(mediaId.toInt())

        coEvery { mediaDatabase.getMediaById(mediaId) } returns localMedia
        coEvery { apiService.searchMediaById(mediaId) } returns Response.success(remoteShow)
        coEvery { mediaDatabase.saveMedia(any()) } returns Unit

        useCase.invoke(mediaId).toList()

        coVerify {
            mediaDatabase.saveMedia(match { it.isFavorite == true })
        }
    }

    private fun createTestMedia(id: String, isFavorite: Boolean = false): Media {
        return Media(
            id = id,
            genres = listOf("Drama"),
            image = "http://example.com/image.jpg",
            language = "English",
            title = "Test Show",
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
            name = "Test Show",
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