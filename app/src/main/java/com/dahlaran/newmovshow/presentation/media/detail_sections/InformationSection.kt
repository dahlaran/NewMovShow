package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.presentation.components.RatingBar

@Composable
fun InfoSection(media: Media?) {
    Column {
        Spacer(modifier = Modifier.height(260.dp))

        Text(
            text = media?.title ?: "",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                modifier = Modifier,
                starsModifier = Modifier.size(18.dp),
                rating = media?.rating?.div(2) ?: 0.0,
            )

            Text(
                modifier = Modifier.padding(
                    horizontal = 4.dp
                ),
                text = "${media?.rating.toString().take(3)}/10",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = media?.getGenresToDisplay() ?: "",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        if ((media?.seasons?.numberOfDayBeforeNextEpisode ?: 0) > 0) {
            Text(
                modifier = Modifier.padding(end = 8.dp), text = pluralStringResource(
                    R.plurals.next_episode_in_days,
                    media?.getDayBeforeNextEpisode() ?: 0,
                    media?.getDayBeforeNextEpisode() ?: 0,
                ), fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 16.sp
            )
        }
    }
}