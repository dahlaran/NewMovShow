package com.dahlaran.newmovshow.domain.usecase

import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.data.local.MediaDatabase
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.GetFavoritesMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetFavoritesMediaUseCaseTest {

    private lateinit var mediaDatabase: MediaDatabase
    private lateinit var useCase: GetFavoritesMediaUseCase

    @Before
    fun setUp() {
        mediaDatabase = mockk()
        useCase = GetFavoritesMediaUseCase(localData = mediaDatabase)
    }

    @Test
    fun `invoke emits loading and success states when operation succeeds`() = runBlocking {
        val expectedResult = listOf(
            createTestMedia("1"),
            createTestMedia("2")
        )

        coEvery {  mediaDatabase.getFavoriteMedias() } returns expectedResult
        val results = useCase.invoke().toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Success && (results[1] as DataState.Success).data == expectedResult)
        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
    }

    @Test
    fun `invoke emits loading and error states when operation fails`() = runBlocking {
        val errorMessage = "Error fetching favorite media"
        coEvery { mediaDatabase.getFavoriteMedias() } throws RuntimeException(errorMessage)

        val results = useCase.invoke().toList()

        assert(results.size == 3)
        assert(results[0] is DataState.Loading && (results[0] as DataState.Loading).isLoading)
        assert(results[1] is DataState.Error)
        assert(results[2] is DataState.Loading && !(results[2] as DataState.Loading).isLoading)
    }

    private fun createTestMedia(id: String,): Media {
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
            isFavorite = true,)
    }
}