package com.dahlaran.newmovshow.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaFavoriteState
import com.dahlaran.newmovshow.presentation.favorite.MediaFavoriteScreenContent
import com.dahlaran.newmovshow.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaFavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testFavoriteMedias = listOf(
        Media(
            id = "1",
            title = "Breaking Bad",
            genres = listOf("Drama", "Crime"),
            image = "https://example.com/image1.jpg",
            language = "English",
            officialSite = null,
            premiered = "2008-01-20",
            rating = 9.5,
            runtime = 47,
            seasons = null,
            status = "Ended",
            summary = "A high school chemistry teacher turned methamphetamine producer",
            type = "Scripted",
            updated = 1234567890,
            url = "https://tvmaze.com/shows/169/breaking-bad",
            weight = 100,
            isFavorite = true
        ),
        Media(
            id = "2",
            title = "The Wire",
            genres = listOf("Drama", "Crime"),
            image = "https://example.com/image2.jpg",
            language = "English",
            officialSite = null,
            premiered = "2002-06-02",
            rating = 9.3,
            runtime = 60,
            seasons = null,
            status = "Ended",
            summary = "The Baltimore drug scene through the eyes of drug dealers and law enforcement",
            type = "Scripted",
            updated = 1234567891,
            url = "https://tvmaze.com/shows/179/the-wire",
            weight = 98,
            isFavorite = true
        )
    )

    @Test
    fun favoritesScreen_displaysTitle() {
        composeTestRule.setContent {
            AppTheme {
                MediaFavoriteScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaFavoriteState(),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("My Favorites")
            .assertIsDisplayed()
    }

    @Test
    fun favoritesScreen_displaysFavoriteMedias() {
        composeTestRule.setContent {
            AppTheme {
                MediaFavoriteScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaFavoriteState(favoriteMedias = testFavoriteMedias),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Breaking Bad")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("The Wire")
            .assertIsDisplayed()
    }

    @Test
    fun favoritesScreen_emptyStateHandling() {
        composeTestRule.setContent {
            AppTheme {
                MediaFavoriteScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaFavoriteState(favoriteMedias = emptyList()),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("My Favorites")
            .assertIsDisplayed()
    }

    @Test
    fun favoritesScreen_gridLayoutWorks() {
        composeTestRule.setContent {
            AppTheme {
                MediaFavoriteScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaFavoriteState(favoriteMedias = testFavoriteMedias),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("Breaking Bad")
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText("The Wire")
            .assertCountEquals(1)
    }
}
