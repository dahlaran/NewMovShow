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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dahlaran.newmovshow.presentation.Route
import com.dahlaran.newmovshow.presentation.favorite.MediaFavoriteScreen
import com.dahlaran.newmovshow.presentation.media.MediaListScreen


@Composable
fun HomeScreen(
    navController: NavHostController
) {
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

    val bottomBarNavController = rememberNavController()
    val currentRoute =
        bottomBarNavController.currentBackStackEntryAsState().value?.destination?.route
            ?: Route.SETTINGS_SCREEN
    val currentRouteIndex = itemList.indexOfFirst { it.route == currentRoute }
    Scaffold(bottomBar = {
        BottomNavBar(
            itemList,
            modifier = Modifier,
            selectedItemIndex = currentRouteIndex,
            onItemClick = { item ->
                if (currentRoute != item.route) {
                    bottomBarNavController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
        )
    }) { paddingValues ->
        BottomNavigationScreens(
            navHostController = navController,
            bottomBarNavController = bottomBarNavController,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun BottomNavigationScreens(
    navHostController: NavHostController,
    bottomBarNavController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = bottomBarNavController,
        startDestination = Route.MEDIA_LIST_SCREEN
    ) {
        composable(Route.MEDIA_LIST_SCREEN) {
            MediaScreen(navHostController)
        }
        composable(Route.FAVORITE_SCREEN) {
            FavoriteScreen(navHostController)
        }
        composable(Route.SETTINGS_SCREEN) {
            SettingsScreen()
        }
    }
}

@Composable
fun MediaScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MediaListScreen(navHostController)
    }
}

@Composable
fun FavoriteScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MediaFavoriteScreen(navHostController)
    }
}

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen")
    }
}