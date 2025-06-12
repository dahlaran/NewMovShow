package com.dahlaran.newmovshow.domain.model

/**
 * Data class representing video information
 *
 * @property id Unique identifier for the video
 * @property name Name or title of the video
 * @property key Unique key for the video, typically used in URLs
 * @property site The site where the video is hosted (e.g., YouTube)
 * @property type The type of video (e.g., trailer, teaser)
 * @property official Indicates if the video is an official release
 * @property quality The quality of the video, represented as an integer
 * @property publishedAt The date and time when the video was published, in ISO 8601 format
 */
data class VideoInfo(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val quality: Int,
    val publishedAt: String?,
    val language: String? = null,
    val country: String,
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
     * Get the thumbnail URL for this video
     *
     * @return Thumbnail URL if site is YouTube, otherwise null
     */
    fun getThumbnailUrl(): String? = when (site.lowercase()) {
        "youtube" -> "https://img.youtube.com/vi/$key/maxresdefault.jpg"
        else -> null
    }

    /**
     * Get the embed URL for this video
     *
     * @return Embed URL if site is YouTube, otherwise null
     */
    fun getEmbedUrl(): String? = when (site.lowercase()) {
        "youtube" -> "https://www.youtube.com/embed/$key"
        else -> null
    }

    /**
     * Check if this video is a trailer or teaser
     *
     * @return true if type is "trailer" or "teaser", false otherwise
     */
    fun isTrailer(): Boolean = type.lowercase() in listOf("trailer", "teaser")
}