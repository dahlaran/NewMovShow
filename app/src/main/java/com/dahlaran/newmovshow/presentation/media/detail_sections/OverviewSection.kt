package com.dahlaran.newmovshow.presentation.media.detail_sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Media

@Composable
fun OverviewSection(media: Media?) {
    Column {
        if (!media?.officialSite.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = 22.dp),
                text = "\"${media?.officialSite ?: ""}\"",
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = stringResource(R.string.summary),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier.padding(horizontal = 22.dp),
            text = media?.getSummaryToDisplay() ?: "",
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 16.sp
        )

    }
}