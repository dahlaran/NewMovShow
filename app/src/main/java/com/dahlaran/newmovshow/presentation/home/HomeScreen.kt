package com.dahlaran.newmovshow.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dahlaran.newmovshow.domain.viewmodel.MainState
import com.dahlaran.newmovshow.presentation.Route
import com.dahlaran.newmovshow.presentation.media.MediaListScreen


@Composable
fun HomeScreen(navController: NavHostController, mainState: MainState) {
    val itemList = listOf(
        BottomNavItem(
            name = "Home",
            route = Route.MEDIA_LIST_SCREEN,
            imageNotSelected = Icons.Outlined.Home,
            imageSelected = Icons.Filled.Home
        ), BottomNavItem(
            name = "Favorites",
            route = Route.FAVORITE_SCREEN,
            imageNotSelected = Icons.Outlined.FavoriteBorder,
            imageSelected = Icons.Filled.Favorite
        ), BottomNavItem(
            name = "Settings",
            route = Route.SETTINGS_SCREEN,
            imageNotSelected = Icons.Outlined.Settings,
            imageSelected = Icons.Filled.Settings
        )
    )
    val selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomBarNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavBar(
            itemList,
            modifier = Modifier,
            selectedItemIndex = selectedItem.value,
            onItemClick = {
                navController.navigate(it.route)
            },
        )
    }) { paddingValues ->
        BottomNavigationScreens(
            navHostController = navController,
            bottomBarNavController = bottomBarNavController,
            modifier = Modifier.padding(paddingValues),
            mainState = mainState,
        )
    }
}

@Composable
fun BottomNavigationScreens(
    navHostController: NavHostController,
    bottomBarNavController: NavHostController,
    modifier: Modifier = Modifier,
    mainState: MainState
) {
    NavHost(
        modifier = modifier,
        navController = bottomBarNavController,
        startDestination = Route.MEDIA_LIST_SCREEN
    ) {
        composable(Route.MEDIA_LIST_SCREEN) {
            MediaScreen(navHostController, mainState)
        }
        composable(Route.FAVORITE_SCREEN) {
            FavoriteScreen()
        }
        composable(Route.SETTINGS_SCREEN) {
            SettingsScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(navHostController: NavHostController, mainState: MainState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MediaListScreen(navHostController, mainState)
    }
}

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Favorite Screen")
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen")
    }
}