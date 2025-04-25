package com.dahlaran.newmovshow.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.dahlaran.newmovshow.domain.model.Media
import com.dahlaran.newmovshow.presentation.DetailScreen

@Composable
fun MediaFavoriteItem(
    media: Media,
    navigationController: NavHostController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 160.dp, height = 200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            navigationController.navigate(DetailScreen(media.id))
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = media.image,
                contentDescription = media.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomCenter),
                text = media.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun MediaFavoriteItemPreview() {
    val media = Media(
        id = "1",
        genres = listOf("Action", "Adventure"),
        title = "Movie 1",
        image = null,
        language = "English", officialSite = null,
        premiered = "2021-01-01",
        rating = 4.5,
        runtime = 120,
        seasons = null,
        status = "Running",
        summary = "This is a movie",
        type = "Movie",
        updated = 0,
        url = "https://static.tvmaze.com/uploads/images/original_untouched/1/4600.jpg",
        weight = 0
    )
    MediaFavoriteItem(media = media, navigationController = rememberNavController())
}
