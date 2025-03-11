package com.dahlaran.newmovshow.presentation.media


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
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
import com.dahlaran.newmovshow.common.data.Constants
import com.dahlaran.newmovshow.common.data.MainEvents
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.viewmodel.MediaViewModel
import com.dahlaran.newmovshow.domain.viewmodel.MediaListState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterial3Api
@Composable
fun MediaListScreen(
    navigationController: NavHostController,
    mediaViewModel: MediaViewModel = hiltViewModel<MediaViewModel>(),
) {
    val state = mediaViewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        mediaViewModel.onEvent(MainEvents.Refresh(""))
    }

    MediaListScreenContent(
        navigationController = navigationController,
        state = state,
        onEvent = mediaViewModel::onEvent
    )
}

@ExperimentalMaterial3Api
@Composable
fun MediaListScreenContent(
    navigationController: NavHostController,
    state: MediaListState,
    onEvent: (event: MainEvents) -> Unit
) {
    fun refresh() = onEvent(MainEvents.Refresh(Constants.GET_MOVIES))
    fun onChange(search: String) = onEvent(MainEvents.Search(search))

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                onChange(it)
            },
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                refresh()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.medias.size) { i ->
                    MediaItem(
                        navigationController,
                        media = state.medias[i],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    if (i < state.medias.size) {
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MediaListScreenContentPreview() {
    val viewModelMock = MediaListState(
            medias = listOf(
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
                )
            )
        )

    MediaListScreenContent(
        navigationController = rememberNavController(),
        state = viewModelMock,
        onEvent = {}
    )
}