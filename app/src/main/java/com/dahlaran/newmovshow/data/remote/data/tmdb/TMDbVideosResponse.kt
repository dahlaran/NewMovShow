package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.VideoInfo
import com.google.gson.annotations.SerializedName

/**
 * Response model for TMDb videos.
 */
data class TMDbVideosResponse(
    val id: Int,
    @SerializedName("results") val videos: List<TMDbVideo>
) {
    /**
     * Get the best trailer available
     * Priority: Official Trailer > Trailer > Teaser > Any video
     *
     * @return The best trailer or null if no videos are available
     */
    fun getBestTrailer(): VideoInfo? {
        if (videos.isEmpty()) return null

        val videoInfos = videos.filter { it.getVideoUrl() != null }.map { it.toVideoInfo() }

        val officialTrailers = videoInfos.filter { it.official && it.type.lowercase() == "trailer" }
        if (officialTrailers.isNotEmpty()) {
            return officialTrailers.maxByOrNull { it.quality }
        }

        val officialVideos = videoInfos.filter { it.official }
        if (officialVideos.isNotEmpty()) {
            return officialVideos.maxByOrNull { it.quality }
        }

        val trailers = videoInfos.filter { it.isTrailer() }
        if (trailers.isNotEmpty()) {
            return trailers.maxByOrNull { it.quality }
        }

        return videoInfos.maxByOrNull { it.quality }
    }
}