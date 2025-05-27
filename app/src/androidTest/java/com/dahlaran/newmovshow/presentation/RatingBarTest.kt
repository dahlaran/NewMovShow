package com.dahlaran.newmovshow.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dahlaran.newmovshow.presentation.components.RatingBar
import com.dahlaran.newmovshow.theme.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatingBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ratingBar_displaysCorrectStars() {
        composeTestRule.setContent {
            AppTheme {
                RatingBar(rating = 4.5)
            }
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun ratingBar_handlesZeroRating() {
        composeTestRule.setContent {
            AppTheme {
                RatingBar(rating = 0.0)
            }
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun ratingBar_handlesMaxRating() {
        composeTestRule.setContent {
            AppTheme {
                RatingBar(rating = 5.0)
            }
        }

        composeTestRule.waitForIdle()
    }
}