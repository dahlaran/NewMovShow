package com.dahlaran.newmovshow.data.remote.data.tvmaze

import com.dahlaran.newmovshow.domain.model.Media
import com.google.gson.annotations.SerializedName

/**
 * Class send by TVMaze API inside TVMazeMedia
 */
data class TVMazeShow(
    @SerializedName("_embedded") val embedded: TVMazeEmbedded?,
    val externals: TVMazeExternals?,
    val genres: List<String>,
    val id: Int,
    val image: TVMazeImage?,
    val language: String?,
    val name: String,
    val network: TVMazeNetwork?,
    val officialSite: String?,
    val premiered: String?,
    val rating: TVMazeRating,
    val runtime: Int,
    val schedule: TVMazeSchedule?,
    val status: String,
    val summary: String?,
    val type: String,
    val updated: Int,
    val url: String,
    val webChannel: Any?,
    val weight: Int
) {
    /**
     * Convert TVMazeShow to Media
     *
     * @return new Show
     */
    fun toMedia(): Media {
        val seasonList = embedded?.toSeasonList()
        return Media(
            id.toString(),
            genres,
            image?.original,
            language,
            name,
            officialSite,
            premiered,
            rating.average,
            runtime,
            seasonList,
            status,
            summary,
            type,
            updated,
            url,
            weight
        )
    }
}