package com.dahlaran.newmovshow.presentation.media

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dahlaran.newmovshow.common.data.Constants
import com.dahlaran.newmovshow.common.data.MainEvents
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallback
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallbackImpl
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.viewmodel.MediaViewModel
import com.dahlaran.newmovshow.domain.viewmodel.MediaViewModelImpl
import com.dahlaran.newmovshow.domain.viewmodel.MediaListState
import com.dahlaran.newmovshow.domain.viewmodel.SearchMediaListViewModel
import com.dahlaran.newmovshow.domain.viewmodel.SearchMediaListViewModelImpl
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@ExperimentalMaterial3Api
@Composable
fun MediaListScreen(
    navigationController: NavHostController,
    mainViewModel: MediaViewModel = hiltViewModel<MediaViewModelImpl>(),
    searchViewModel: SearchMediaListViewModel = hiltViewModel<SearchMediaListViewModelImpl>(),
) {
    val mainState = mainViewModel.mainState.collectAsState().value

    val state = searchViewModel.state
    val context = LocalContext.current

    fun refresh() = mainViewModel.onEvent(MainEvents.Refresh(Constants.GET_MOVIES))
    fun onChange(search: String) =
        searchViewModel.getSearchMediaByTitle(search, FrontSimpleCallbackImpl(context))

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = searchViewModel.state.collectAsState().value.isLoading)

    LaunchedEffect(Unit) {
        mainViewModel.onEvent(MainEvents.Refresh(""))
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.collectAsState().value.searchQuery,
            onValueChange = {
                Toast.makeText(context, "Search: $it", Toast.LENGTH_SHORT).show()
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
                items(mainState.medias.size) { i ->
                    MediaItem(
                        navigationController,
                        media = mainState.medias[i],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    if (i < mainState.medias.size) {
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
fun MediaListScreenPreview() {
    val viewModelMock = object : SearchMediaListViewModel {
        override val state: StateFlow<MediaListState> = MutableStateFlow(
            MediaListState(
                searchedMedias = listOf(
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
        ).asStateFlow()

        override fun getSearchMediaByTitle(title: String, callback: FrontSimpleCallback?) {
           // Do Nothing
        }
    }

    MediaListScreen(
        navigationController = rememberNavController(),
        searchViewModel = viewModelMock
    )
}