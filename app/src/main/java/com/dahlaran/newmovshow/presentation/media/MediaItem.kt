package com.dahlaran.newmovshow.presentation.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.presentation.Route
import timber.log.Timber

@Composable
fun MediaItem(
    navigationController: NavHostController,
    media: Media,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigationController.navigate("${Route.MEDIA_DETAIL_SCREEN}/${media.id}")
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = media.image,
            contentDescription = "${media.title} image"
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = media.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}