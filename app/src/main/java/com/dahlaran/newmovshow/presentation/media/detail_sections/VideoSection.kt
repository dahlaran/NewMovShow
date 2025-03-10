package com.dahlaran.newmovshow.presentation.media.detail_sections

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dahlaran.newmovshow.R
import com.dahlaran.newmovshow.domain.model.Media

@Composable
fun VideoSection(media: Media?) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable {
                Toast
                    .makeText(
                        context,
                        context.getString(R.string.no_video_is_available_at_the_moment),
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }, shape = RoundedCornerShape(0), elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = media?.image, contentDescription = stringResource(id = R.string.trailer)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(50.dp)
                    .alpha(0.6f)
                    .background(Color.LightGray)
            )
            Icon(
                Icons.Rounded.PlayArrow,
                contentDescription = stringResource(id = R.string.trailer_play),
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )

        }
    }
}
