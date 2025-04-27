package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.AddFavoriteMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddFavoriteMediaUseCaseTest {

    private lateinit var mediaDatabase: MediaDatabase
    private lateinit var useCase: AddFavoriteMediaUseCase

    @Before
    fun setup() {
        mediaDatabase = mockk()
        useCase = AddFavoriteMediaUseCase(localData = mediaDatabase)
    }

    @Test
    fun `invoke emits loading and success states when operation succeeds`() = runBlocking {
        val mediaId = "123"
        val updatedMedia = createTestMedia(mediaId, true)
        coEvery { mediaDatabase.addFavoriteMedia(mediaId) } returns updatedMedia

        val results = useCase.invoke(mediaId).toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == updatedMedia)
        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
    }

    @Test
    fun `invoke emits loading and error states when operation fails`() = runBlocking {
        val mediaId = "123"
        val errorMessage = "Error adding favorite media"
        coEvery { mediaDatabase.addFavoriteMedia(mediaId) } throws RuntimeException(errorMessage)

        val results = useCase.invoke(mediaId).toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Error)
        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
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
            isFavorite = isFavorite,)
    }
}