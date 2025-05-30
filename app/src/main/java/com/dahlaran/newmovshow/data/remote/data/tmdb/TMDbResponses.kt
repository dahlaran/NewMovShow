package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.Media
import com.google.gson.annotations.SerializedName

data class TMDbMovieResponse(
    val page: Int,
    val results: List<TMDbMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)