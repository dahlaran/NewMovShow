package com.dahlaran.newmovshow.data.remote.data.tvmaze

import com.dahlaran.newmovshow.domain.model.Media

/**
 * Class send by TVMaze API
 */
data class TVMazeMedia(
    val score: Double,
    val show: TVMazeShow
) {
    fun toMedia(): Media {
        return show.toMedia()
    }
}

