package com.dahlaran.newmovshow.data.remote.data.tmdb

data class TMDbGenre(
    val id: Int,
    val name: String
) {
    override fun toString(): String = name
}