package com.dahlaran.newmovshow.presentation.media.detail_sections

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Media
import androidx.core.net.toUri
import timber.log.Timber

@Composable
fun VideoSection(media: Media?) {
    val context = LocalContext.current
    val bestTrailer = media?.video

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                bestTrailer?.getVideoUrl()?.let { url ->
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Timber.e("Error opening video: ${e.message}")
                        Toast.makeText(context,
                            context.getString(R.string.error_opening_video),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } ?: run {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_video_is_available_at_the_moment),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            // Use trailer thumbnail if available, otherwise use media poster
            val imageUrl = bestTrailer?.getThumbnailUrl() ?: media?.image

            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(id = R.string.trailer),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Play button overlay
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(60.dp)
                    .alpha(0.8f)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.PlayArrow,
                    contentDescription = stringResource(id = R.string.trailer_play),
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Trailer info overlay (bottom)
            bestTrailer?.let { trailer ->
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.7f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = trailer.name,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${trailer.type} • ${trailer.quality}p",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
