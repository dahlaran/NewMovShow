package com.dahlaran.newmovshow.presentation.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.dahlaran.newmovshow.R
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Rating bar to display the rating of a movie or a tv show
 *
 * @param modifier Modifier of the full bar
 * @param starsModifier Modifier of each star
 * @param rating Ratting of the movie or tv show in double (ex: 4.5)
 * @param stars Number of stars to display (default is 5)
 * @param starsColor Color of each star (default is Yellow)
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {

    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = (rating.rem(1) != 0.0)

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_star_half),
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = starsModifier,
                painter = painterResource(id = R.drawable.ic_star_empty),
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}