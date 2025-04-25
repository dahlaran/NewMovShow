package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.domain.model.Media

data class MediaDetailState(
    val media: Media? = null,
    val isLoading: Boolean = false,
    val error: Error? = null
)