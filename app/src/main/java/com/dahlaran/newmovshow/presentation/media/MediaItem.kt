package com.dahlaran.newmovshow.presentation.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.presentation.DetailScreen
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
                navigationController.navigate(DetailScreen(media.id))
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = media.image,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
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