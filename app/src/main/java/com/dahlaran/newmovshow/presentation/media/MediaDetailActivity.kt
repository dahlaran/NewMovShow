package com.dahlaran.newmovshow.presentation.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.dahlaran.newmovshow.domain.viewmodel.MediaDetailViewModel
import com.dahlaran.newmovshow.presentation.IntentExtra
import com.dahlaran.newmovshow.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MediaDetailActivity : ComponentActivity() {

    private lateinit var mediaDetailViewModel: MediaDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaDetailViewModel = ViewModelProvider(this)[MediaDetailViewModel::class.java]
        intent.getStringExtra(IntentExtra.MEDIA_ID)?.let { mediaId ->
            mediaDetailViewModel.getMediaDetail(mediaId)
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MediaDetailScreen(mediaDetailScreenState = mediaDetailViewModel.state)
                }
            }

        }
    }
}