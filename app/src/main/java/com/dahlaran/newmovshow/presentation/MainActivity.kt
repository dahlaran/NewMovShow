package com.dahlaran.newmovshow.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailViewModel
import com.dahlaran.newmovshow.presentation.home.HomeScreen
import com.dahlaran.newmovshow.presentation.media.MediaDetailScreen
import com.dahlaran.newmovshow.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(navController = navController)
        }
        composable<DetailScreen> {
            val mediaDetailsViewModel = hiltViewModel<MediaDetailViewModel>()
            val mediaDetailsScreenState = mediaDetailsViewModel.state
            val id: String = it.toRoute<DetailScreen>().id

            if (mediaDetailsViewModel.state.media?.id != id && !mediaDetailsViewModel.state.isLoading) {
                Timber.e("Media not loaded")
                mediaDetailsViewModel.getMediaDetail(mediaId = id)
            } else {
                Timber.e("Media already loaded")
            }
            MediaDetailScreen(
                mediaDetailScreenState = mediaDetailsScreenState,
                navController = navController
            )
        }
    }
}


@Serializable
object HomeScreen

@Serializable
data class DetailScreen(val id: String)