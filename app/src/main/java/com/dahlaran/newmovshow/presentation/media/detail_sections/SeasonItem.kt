package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Episode
import com.dahlaran.newmovshow.domain.model.Season


@Composable
fun SeasonItem(season: Season) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpanded = !isExpanded
                    }.padding(12.dp)
            ) {
                Text(text = "Season " + season.seasonNumber, modifier = Modifier.weight(1f))
                if (isExpanded) {
                    Icon(Icons.Default.KeyboardArrowUp,  contentDescription = stringResource(id = R.string.expanded))
                } else {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(id = R.string.collapsed))
                }
            }
             Column {
                season.episodes.forEachIndexed { index, episode ->
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically( initialOffsetY =  { it * (index + 1) / 2 } ) + expandVertically() + fadeIn(),
                        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        EpisodeItem(episode = episode)
                    }
                }
            }

        }
    }
}

@Composable
@Preview
fun SeasonItemPreview() {
    val season = Season(
        seasonNumber = 1,
        episodes = listOf(
            Episode(
                id = 1,
                name = "Episode 1",
                summary = "Summary of episode 1",
                airdate = "2023-11-01",
                number = 1,
                season = 1,
                airstamp = "2023-11-01",
                airtime = "2023-11-01",
                imageUrl = "",
                runtime = 2,
                time = null,
                url = "",
            ),
            Episode(
                id = 2,
                name = "Episode 2",
                summary = "Summary of episode 2",
                airdate = "2023-11-02",
                number = 2,
                season = 1,
                airstamp = "2023-11-02",
                airtime = "2023-11-02",
                imageUrl = "",
                runtime = 2,
                time = null,
                url = "",
            )
        )
    )
    SeasonItem(season)
}