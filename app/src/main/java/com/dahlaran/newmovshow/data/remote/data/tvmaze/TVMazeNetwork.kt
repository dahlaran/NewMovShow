package com.dahlaran.newmovshow.data.remote.data.tvmaze

/**
 * Data class from TVMaze API
 */
data class TVMazeNetwork(
    val country: TVMazeCountry,
    val id: Int,
    val name: String
)