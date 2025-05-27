package com.dahlaran.newmovshow.presentation

import app.cash.turbine.test
import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.common.data.ErrorCode
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.use_case.AddFavoriteMediaUseCase
import com.dahlaran.newmovshow.domain.use_case.GetMediaUseCase
import com.dahlaran.newmovshow.domain.use_case.RemoveFavoriteMediaUseCase
import com.dahlaran.newmovshow.domain.viewmodel.DetailEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailViewModel
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
class MediaDetailViewModelTest {

    private lateinit var viewModel: MediaDetailViewModel
    private lateinit var getMediaUseCase: GetMediaUseCase
    private lateinit var addFavoriteUseCase: AddFavoriteMediaUseCase
    private lateinit var removeFavoriteUseCase: RemoveFavoriteMediaUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getMediaUseCase = mockk()
        addFavoriteUseCase = mockk()
        removeFavoriteUseCase = mockk()
        viewModel = MediaDetailViewModel(getMediaUseCase, addFavoriteUseCase, removeFavoriteUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ArriveOnMedia event loads media details`() = runTest {
        val mediaId = "123"
        val testMedia = createTestMedia(mediaId)

        coEvery { getMediaUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(testMedia))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(DetailEvent.ArriveOnMedia(mediaId))

        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            val loadedState = awaitItem()
            assertEquals(testMedia, loadedState.media)

            val finalState = awaitItem()
            assertFalse(finalState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `AddFavorite event adds media to favorites`() = runTest {
        val mediaId = "123"
        val media = createTestMedia(mediaId, false)
        val favoriteMedia = media.copy(isFavorite = true)

        viewModel.onEvent(DetailEvent.ArriveOnMedia(mediaId))
        coEvery { getMediaUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Success(media))
        }

        coEvery { addFavoriteUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(favoriteMedia))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(DetailEvent.AddFavorite)

        viewModel.state.test {
            awaitItem()
            val updatedState = awaitItem()
            assertEquals(favoriteMedia, updatedState.media)
            assertFalse(updatedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `RemoveFavorite event removes media from favorites`() = runTest {
        val mediaId = "123"
        val favoriteMedia = createTestMedia(mediaId, true)
        val regularMedia = favoriteMedia.copy(isFavorite = false)

        coEvery { getMediaUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Success(favoriteMedia))
        }
        viewModel.onEvent(DetailEvent.ArriveOnMedia(mediaId))

        coEvery { removeFavoriteUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Loading(true))
            emit(DataState.Success(regularMedia))
            emit(DataState.Loading(false))
        }

        viewModel.onEvent(DetailEvent.RemoveFavorite)

        viewModel.state.test {
            awaitItem()
            val updatedState = awaitItem()
            assertEquals(regularMedia, updatedState.media)
            assertFalse(updatedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Refresh event reloads current media`() = runTest {
        val mediaId = "123"
        val media = createTestMedia(mediaId)

        coEvery { getMediaUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Success(media))
        }
        viewModel.onEvent(DetailEvent.ArriveOnMedia(mediaId))

        viewModel.onEvent(DetailEvent.Refresh)

        viewModel.state.test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error state is properly handled`() = runTest {
        val mediaId = "123"
        val error = Error(ErrorCode.CODE_NETWORK_PROBLEM)

        coEvery { addFavoriteUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Error(error))
        }

        coEvery { getMediaUseCase.invoke(mediaId) } returns flow {
            emit(DataState.Success(createTestMedia(mediaId)))
        }
        viewModel.onEvent(DetailEvent.ArriveOnMedia(mediaId))

        viewModel.onEvent(DetailEvent.AddFavorite)

        viewModel.state.test {
            awaitItem()
            val errorState = awaitItem()
            assertEquals(error, errorState.error)

            cancelAndIgnoreRemainingEvents()
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
}