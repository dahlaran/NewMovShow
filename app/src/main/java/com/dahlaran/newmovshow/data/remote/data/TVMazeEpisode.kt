package com.dahlaran.newmovshow.data.remote.data

import com.dahlaran.newmovshow.domain.model.Episode
import java.util.Date

/**
 * Data class from TVMaze API
 */
data class TVMazeEpisode(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val image: TVMazeImage,
    val name: String,
    val number: Int,
    val runtime: Int,
    val season: Int,
    val summary: String,
    var time: Date?,
    val url: String
) {
    fun toEpisode(): Episode {
        return Episode(
            airdate,
            airstamp,
            airtime,
            id,
            image.original,
            name,
            number,
            runtime,
            season,
            summary,
            time,
            url
        )
    }
}