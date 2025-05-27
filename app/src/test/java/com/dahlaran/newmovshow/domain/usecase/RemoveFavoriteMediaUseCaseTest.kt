package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.RemoveFavoriteMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoveFavoriteMediaUseCaseTest {

    private lateinit var mediaDatabase: MediaDatabase
    private lateinit var useCase: RemoveFavoriteMediaUseCase

    @Before
    fun setup() {
        mediaDatabase = mockk()
        useCase = RemoveFavoriteMediaUseCase(mediaDatabase)
    }

    @Test
    fun `invoke emits loading and success states when operation succeeds`() = runBlocking {
        val mediaId = "123"
        val updatedMedia = createTestMedia(mediaId, false)
        coEvery { mediaDatabase.removeFavoriteMedia(mediaId) } returns updatedMedia

        val results = useCase.invoke(mediaId).toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == updatedMedia)
        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
    }

    @Test
    fun `invoke emits error when media not found`() = runBlocking {
        val mediaId = "123"
        coEvery { mediaDatabase.removeFavoriteMedia(mediaId) } returns null

        val results = useCase.invoke(mediaId).toList()

        assert(results.any { it is DataState.Error })
    }

    @Test
    fun `invoke emits error when exception occurs`() = runBlocking {
        val mediaId = "123"
        coEvery { mediaDatabase.removeFavoriteMedia(mediaId) } throws RuntimeException("Database error")

        val results = useCase.invoke(mediaId).toList()

        assert(results.any { it is DataState.Error })
    }

    private fun createTestMedia(id: String, isFavorite: Boolean): Media {
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
}