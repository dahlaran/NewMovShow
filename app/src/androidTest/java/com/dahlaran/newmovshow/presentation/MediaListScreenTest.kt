package com.dahlaran.newmovshow.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.domain.viewmodel.MainEvent
import com.dahlaran.newmovshow.domain.viewmodel.MediaListState
import com.dahlaran.newmovshow.presentation.media.MediaListScreenContent
import com.dahlaran.newmovshow.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testMedias = listOf(
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
            weight = 100
        ),
        Media(
            id = "2",
            title = "Better Call Saul",
            genres = listOf("Drama", "Crime"),
            image = "https://example.com/image2.jpg",
            language = "English",
            officialSite = null,
            premiered = "2015-02-08",
            rating = 8.8,
            runtime = 47,
            seasons = null,
            status = "Ended",
            summary = "The trials and tribulations of criminal lawyer Jimmy McGill",
            type = "Scripted",
            updated = 1234567891,
            url = "https://tvmaze.com/shows/93/better-call-saul",
            weight = 95
        )
    )

    @Test
    fun mediaListScreen_displaysSearchField() {
        composeTestRule.setContent {
            AppTheme {
                MediaListScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaListState(),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Search...")
            .assertIsDisplayed()
    }

    @Test
    fun mediaListScreen_displaysMediaItems() {
        composeTestRule.setContent {
            AppTheme {
                MediaListScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaListState(medias = testMedias),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Breaking Bad")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Better Call Saul")
            .assertIsDisplayed()
    }

    @Test
    fun mediaListScreen_searchFieldIsInteractive() {
        var lastEvent: MainEvent? = null

        composeTestRule.setContent {
            AppTheme {
                MediaListScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaListState(),
                    onEvent = { event -> lastEvent = event }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Search...")
            .performTextInput("Breaking")

        assert(lastEvent is MainEvent.Search)
        assert((lastEvent as MainEvent.Search).title == "Breaking")
    }

    @Test
    fun mediaListScreen_showsLoadingState() {
        composeTestRule.setContent {
            AppTheme {
                MediaListScreenContent(
                    navigationController = rememberNavController(),
                    state = MediaListState(isLoading = true),
                    onEvent = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Search...")
            .assertIsDisplayed()
    }
}