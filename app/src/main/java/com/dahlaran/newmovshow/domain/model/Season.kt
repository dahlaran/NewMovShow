package com.dahlaran.newmovshow.domain.model


data class Season(
    val seasonNumber: Int,
    val episodes: List<Episode>
)