package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dahlaran.newmovshow.domain.model.SeasonList


@Composable
fun SeasonListSection(
    seasonList: SeasonList,
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        seasonList.seasons?.let { seasons ->
            items(seasons) {
                SeasonItem(season = it)
            }
        }
    }
}