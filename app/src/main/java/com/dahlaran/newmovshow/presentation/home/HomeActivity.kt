package com.dahlaran.newmovshow.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailViewModel
import com.dahlaran.newmovshow.presentation.IntentExtra
import com.dahlaran.newmovshow.presentation.Route
import com.dahlaran.newmovshow.presentation.media.MediaDetailActivity
import com.dahlaran.newmovshow.presentation.media.MediaDetailScreen
import com.dahlaran.newmovshow.presentation.media.MediaListScreen
import com.dahlaran.newmovshow.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val itemList = listOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(bottomBar = {
                        BottomNavigationBar(itemList,
                            navHostController = navController,
                            modifier = Modifier,
                            onItemClick = {
                                navController.navigate(it.route)
                            })
                    }) {
                        Navigation(
                            navHostController = navController, modifier = Modifier.padding(it)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Navigation(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navHostController, startDestination = Route.MEDIA_LIST_SCREEN) {
        composable(Route.MEDIA_LIST_SCREEN) {
            MediaScreen(navHostController)
        }
        composable(Route.FAVORITE_SCREEN) {
            FavoriteScreen()
        }
        composable(Route.SETTINGS_SCREEN) {
            SettingsScreen()
        }

        composable(
            "${Route.MEDIA_DETAIL_SCREEN}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id: String = it.arguments?.getString("id") ?: "0"
            val context = LocalContext.current
            context.startActivity(Intent(context, MediaDetailActivity::class.java).apply { putExtra(
                IntentExtra.MEDIA_ID, id) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    itemList: List<BottomNavItem>,
    navHostController: NavHostController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        itemList.forEach { item ->
            val isSelected = navBackStackEntry?.destination?.route == item.route
            NavigationBarItem(
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            }) {
                                Icon(
                                    imageVector = if (isSelected) item.imageSelected else item.imageNotSelected,
                                    contentDescription = item.name
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (isSelected) item.imageSelected else item.imageNotSelected,
                                contentDescription = item.name
                            )
                        }
                    }
                },
                label = {
                    Text(text = item.name, textAlign = TextAlign.Center)
                },
                selected = isSelected,
                onClick = {
                    onItemClick(item)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        MediaListScreen(navHostController)
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