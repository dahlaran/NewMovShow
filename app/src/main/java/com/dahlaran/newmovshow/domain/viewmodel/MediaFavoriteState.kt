package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.data.Error
import com.dahlaran.newmovshow.domain.model.Media

data class MediaFavoriteState(
    val isLoading: Boolean = false,
    val favoriteMedias: List<Media> = emptyList(),
    val error: Error? = null,
)