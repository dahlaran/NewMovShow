package com.dahlaran.newmovshow.data.remote.data

/**
 * Data class from TVMaze API
 */
data class TVMazeSchedule(
    val days: List<String>,
    val time: String
)