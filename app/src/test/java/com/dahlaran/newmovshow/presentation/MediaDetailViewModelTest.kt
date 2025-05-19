package com.dahlaran.newmovshow.domain.viewmodel

import app.cash.turbine.test
import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import com.dahlaran.newmovshow.domain.use_case.SearchMediaByTitleUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class MediaViewModelTest {

    private lateinit var viewModel: MediaViewModel
    private lateinit var getMediasUseCase: GetMediasUseCase
    private lateinit var searchMediaByTitleUseCase: SearchMediaByTitleUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediasUseCase = mockk()
        searchMediaByTitleUseCase = mockk()
        viewModel = MediaViewModel(getMediasUseCase, searchMediaByTitleUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when refresh event is triggered, medias are loaded`() = runTest {
        // Given
        val fakeMedias = listOf(
            Media(
                id = "1",
                genres = listOf("Drama"),
                image = "imageUrl",
                language = "English",
                title = "Test Show",
                officialSite = "site",
                premiered = "2020-01-01",
                rating = 8.5,
                runtime = 60,
                seasons = null,
                status = "Running",
                summary = "Test summary",
                type = "show",
                updated = 123456,
                url = "test-url",
                weight = 100
            )
        )

        coEvery { getMediasUseCase.invoke(0) } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(fakeMedias))
            emit(DataState.Loading(false))
        }

        // When
        viewModel.onEvent(MainEvent.Refresh(""))

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            val loadedState = awaitItem()
            assertEquals(fakeMedias, loadedState.medias)
            assertEquals(1, loadedState.mediaPage)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when search event is triggered, search results are loaded`() = runTest {
        // Given
        val searchTitle = "Breaking Bad"
        val fakeSearchResults = listOf(
            Media(
                id = "2",
                genres = listOf("Drama", "Crime"),
                image = "imageUrl2",
                language = "English",
                title = "Breaking Bad",
                officialSite = "site2",
                premiered = "2008-01-20",
                rating = 9.5,
                runtime = 45,
                seasons = null,
                status = "Ended",
                summary = "Chemistry teacher turns to crime",
                type = "show",
                updated = 789123,
                url = "test-url2",
                weight = 98
            )
        )

        coEvery { searchMediaByTitleUseCase.invoke(searchTitle, 0) } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(fakeSearchResults))
            emit(DataState.Loading(false))
        }

        // When
        viewModel.onEvent(MainEvent.Search(searchTitle))

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)
            assertEquals(searchTitle, initialState.searchQuery)
            assertEquals(0, initialState.mediaPage)
            assertTrue(initialState.medias.isEmpty())

            val loadedState = awaitItem()
            assertEquals(fakeSearchResults, loadedState.medias)
            assertEquals(1, loadedState.mediaPage)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}