package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Episode


@Composable
fun EpisodeItem(
    episode: Episode
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = episode.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )

            if (isExpanded) {
                Text(
                    text = episode.getTextSummary(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            } else {
                Text(
                    text = stringResource(R.string.show_summary),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }
    }
}

@Composable
@Preview
fun EpisodeItemPreview() {
    EpisodeItem(
        episode = Episode(
            id = 1,
            name = "Episode 1",
            summary = "Summary of episode 1",
            airdate = "2023-10-01",
            number = 1,
            season = 1,
            airstamp = "2023-10-01",
            airtime = "2023-10-01",
            imageUrl = "",
            runtime = 2,
            time = null,
            url = "",
        )
    )
}