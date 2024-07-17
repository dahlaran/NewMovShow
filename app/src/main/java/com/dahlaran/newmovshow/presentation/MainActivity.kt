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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailViewModel
import com.dahlaran.newmovshow.presentation.home.HomeScreen
import com.dahlaran.newmovshow.presentation.media.MediaDetailScreen
import com.dahlaran.newmovshow.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

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

    val mediaDetailsViewModel = hiltViewModel<MediaDetailViewModel>()
    val mediaDetailsScreenState = mediaDetailsViewModel.state
    NavHost(
        navController = navController, startDestination = Route.MEDIA_LIST_SCREEN
    ) {
        composable(Route.MEDIA_LIST_SCREEN) {
            HomeScreen(navController = navController)
        }

        composable(
            "${Route.MEDIA_DETAIL_SCREEN}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id: String = it.arguments?.getString("id") ?: "0"
            mediaDetailsViewModel.getMediaDetail(mediaId = id)
            MediaDetailScreen(mediaDetailScreenState = mediaDetailsScreenState)
        }
    }
}