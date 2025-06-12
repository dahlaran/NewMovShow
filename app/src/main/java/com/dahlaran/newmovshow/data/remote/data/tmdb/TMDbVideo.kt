package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.VideoInfo
import com.google.gson.annotations.SerializedName

data class TMDbVideo(
    val id: String,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @SerializedName("published_at") val publishedAt: String?
) {
    /**
     * Get the full YouTube URL for this video
     *
     * @return YouTube URL if site is YouTube, otherwise null
     */
    fun getVideoUrl(): String? = when (site.lowercase()) {
        "youtube" -> "https://www.youtube.com/watch?v=$key"
        else -> null
    }

    /**
     * Get YouTube thumbnail URL
     *
     * @return YouTube thumbnail URL if site is YouTube, otherwise null
     */
    fun getThumbnailUrl(): String? = when (site.lowercase()) {
        "youtube" -> "https://img.youtube.com/vi/$key/maxresdefault.jpg"
        else -> null
    }

    /**
     * Get embeddable YouTube URL for WebView
     *
     * @return YouTube embed URL if site is YouTube, otherwise null
     */
    fun getEmbedUrl(): String? = when (site.lowercase()) {
        "youtube" -> "https://www.youtube.com/embed/$key"
        else -> null
    }

    /**
     * Check if this is a trailer
     */
    fun isTrailer(): Boolean = type.lowercase() in listOf("trailer", "teaser")

    /**
     * Check if this is high quality (720p or higher)
     */
    fun isHighQuality(): Boolean = size >= 720

    /**
     * Convert to VideoInfo domain model
     *
     * @return VideoInfo object with relevant fields mapped
     */
    fun toVideoInfo(): VideoInfo {
        return VideoInfo(
            id = id,
            language = language,
            country = country,
            key = key,
            name = name,
            site = site,
            type = type,
            official = official,
            publishedAt = publishedAt,
            quality = size
        )
    }
}