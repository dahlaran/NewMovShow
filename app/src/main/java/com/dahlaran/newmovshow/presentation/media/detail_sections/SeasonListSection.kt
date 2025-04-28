package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dahlaran.newmovshow.domain.model.SeasonList


@Composable
fun SeasonListSection(
    seasonList: SeasonList,
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        seasonList.seasons?.let { seasons ->
            seasons.forEach { season ->
                SeasonItem(season = season)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}