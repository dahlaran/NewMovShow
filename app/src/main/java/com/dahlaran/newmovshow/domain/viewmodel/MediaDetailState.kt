package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.domain.model.Media

data class MediaDetailState(
    val media: Media? = null,
    val isLoading: Boolean = false)