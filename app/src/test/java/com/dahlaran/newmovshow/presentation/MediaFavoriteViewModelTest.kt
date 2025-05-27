package com.dahlaran.newmovshow.presentation

import app.cash.turbine.test
import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.common.data.ErrorCode
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.GetFavoritesMediaUseCase
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteViewModel
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
class MediaFavoriteViewModelTest {

    private lateinit var viewModel: MediaFavoriteViewModel
    private lateinit var getFavoritesUseCase: GetFavoritesMediaUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFavoritesUseCase = mockk()
        viewModel = MediaFavoriteViewModel(getFavoritesUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ArriveOnFavorite event loads favorite medias`() = runTest {
        val favoriteMedias = listOf(
            createTestMedia("1", true),
            createTestMedia("2", true)
        )

        coEvery { getFavoritesUseCase.invoke() } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(favoriteMedias))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(MediaFavoriteEvent.ArriveOnFavorite)

        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            val loadedState = awaitItem()
            assertEquals(favoriteMedias, loadedState.favoriteMedias)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Refresh event reloads favorite medias`() = runTest {
        val favoriteMedias = listOf(createTestMedia("1", true))

        coEvery { getFavoritesUseCase.invoke() } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(favoriteMedias))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(MediaFavoriteEvent.Refresh)

        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            val loadedState = awaitItem()
            assertEquals(favoriteMedias, loadedState.favoriteMedias)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error state is properly handled`() = runTest {
        val error = Error(ErrorCode.CODE_NETWORK_PROBLEM)

        coEvery { getFavoritesUseCase.invoke() } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Error(error))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(MediaFavoriteEvent.ArriveOnFavorite)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val errorState = awaitItem()
            assertEquals(error, errorState.error)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `empty favorites list is handled correctly`() = runTest {
        coEvery { getFavoritesUseCase.invoke() } returns flow {
            emit(DataState.Success(emptyList()))
        }

        viewModel.onEvent(MediaFavoriteEvent.ArriveOnFavorite)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue(state.favoriteMedias.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
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
}