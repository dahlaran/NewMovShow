package com.dahlaran.newmovshow.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteState
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun MediaFavoriteScreen(
    navigationController: NavHostController,
    mediaViewModel: MediaFavoriteViewModel = hiltViewModel<MediaFavoriteViewModel>(),
) {
    val state = mediaViewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        mediaViewModel.onEvent(MediaFavoriteEvent.Refresh)
    }

    MediaFavoriteScreenContent(
        navigationController = navigationController,
        state = state,
        onEvent = mediaViewModel::onEvent
    )
}


@Composable
fun MediaFavoriteScreenContent(
    navigationController: NavHostController,
    state: MediaFavoriteState,
    onEvent: (MediaFavoriteEvent) -> Unit
) {

    fun refresh() = onEvent(MediaFavoriteEvent.Refresh)

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = { refresh() }) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.favoriteMedias, key = { it.id }) { media ->
                    MediaFavoriteItem(
                        media = media,
                        navigationController = navigationController
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MediaListScreenContentPreview() {
    val viewModelMock = MediaFavoriteState(
        favoriteMedias = listOf(
            Media(
                id = "1",
                genres = listOf("Action", "Adventure"),
                title = "Movie 1",
                image = null,
                language = "English", officialSite = null,
                premiered = "2021-01-01",
                rating = 4.5,
                runtime = 120,
                seasons = null,
                status = "Running",
                summary = "This is a movie",
                type = "Movie",
                updated = 0,
                url = "",
                weight = 0
            ),
            Media(
                id = "2",
                genres = listOf("Action", "Adventure"),
                title = "Movie 2",
                image = null,
                language = "English", officialSite = null,
                premiered = "2021-01-01",
                rating = 4.5,
                runtime = 120,
                seasons = null,
                status = "Running",
                summary = "This is a movie",
                type = "Movie",
                updated = 0,
                url = "",
                weight = 0
            )
        )
    )

    MediaFavoriteScreenContent(
        navigationController = rememberNavController(),
        state = viewModelMock,
        onEvent = {}
    )
}



