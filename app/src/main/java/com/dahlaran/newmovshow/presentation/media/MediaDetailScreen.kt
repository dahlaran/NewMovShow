package com.dahlaran.newmovshow.presentation.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dahlaran.newmovshow.domain.viewmodel.DetailEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailState
import com.dahlaran.newmovshow.presentation.media.detail_sections.InfoSection
import com.dahlaran.newmovshow.presentation.media.detail_sections.OverviewSection
import com.dahlaran.newmovshow.presentation.media.detail_sections.PosterSection
import com.dahlaran.newmovshow.presentation.media.detail_sections.VideoSection
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MediaDetailScreen(
    mediaDetailScreenState: MediaDetailState,
    navController: NavHostController,
    onEvent: (DetailEvent) -> Unit,
) {
    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = mediaDetailScreenState.isLoading)

    fun refresh() = onEvent(DetailEvent.Refresh)
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        refresh()
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                mediaDetailScreenState.media?.let { media ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        VideoSection(media = media)

                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            PosterSection(
                                mediaTitle = media.title, mediaImageUrl = media.image ?: ""
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            InfoSection(media = media)

                            Spacer(modifier = Modifier.width(8.dp))
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    OverviewSection(media = media)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun MediaDetailScreenPreview() {
    MediaDetailScreen(mediaDetailScreenState = MediaDetailState(),
        navController = rememberNavController(),
        onEvent = { })
}