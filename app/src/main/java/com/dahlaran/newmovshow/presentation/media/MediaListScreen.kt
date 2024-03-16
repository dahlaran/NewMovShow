package com.dahlaran.newmovshow.presentation.media

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallbackImpl
import com.dahlaran.newmovshow.domain.viewmodel.MediaListViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterial3Api
@Composable
fun MediaListScreen(
    navigationController: NavHostController,
    viewModel: MediaListViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current

    fun refresh() = viewModel.getMedias(0, FrontSimpleCallbackImpl(context))
    fun onChange(search: String) = viewModel.getSearchMediaByTitle(search, FrontSimpleCallbackImpl(context))

    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = viewModel.state.isLoading)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
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