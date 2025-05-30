package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.Media
import com.google.gson.annotations.SerializedName

/**
 * [TMDbTVShow] is a data class representing a TV show from The Movie Database (TMDb) API.
 * This class is used to map the JSON response from the TMDb API to a Kotlin data structure.
 *
 * @property id The unique identifier for the TV show.
 * @property name The name of the TV show.
 * @property overview A brief description of the TV show.
 * @property posterPath The path to the poster image of the TV show.
 * @property backdropPath The path to the backdrop image of the TV show.
 * @property firstAirDate The date when the TV show first aired.
 * @property voteAverage The average rating of the TV show.
 * @property voteCount The total number of votes the TV show has received.
 * @property genreIds A list of genre IDs associated with the TV show.
 * @property originCountry A list of countries where the TV show originated.
 * @property originalLanguage The original language of the TV show.
 * @property originalName The original name of the TV show.
 * @property popularity The popularity score of the TV show.
 */
data class TMDbTVShow(
    val id: Int,
    val name: String,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    val popularity: Double
) {
    /**
     * Converts the TMDbTVShow instance to a Media instance.
     *
     * @return A Media instance containing the relevant data from the TMDbTVShow.
     */
    fun toMedia(): Media {
        return Media(
            id = "tmdb_tv_$id",
            genres = genreIds.map { getGenreName(it) },
            image = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
            language = originalLanguage,
            title = name,
            officialSite = null,
            premiered = firstAirDate,
            rating = voteAverage,
            runtime = 0,
            seasons = null,
            status = "Unknown",
            summary = overview,
            type = "TV Show",
            updated = 0,
            url = "https://www.themoviedb.org/tv/$id",
            weight = popularity.toInt()
        )
    }

    private fun getGenreName(genreId: Int): String {
        return when (genreId) {
            10759 -> "Action & Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            10762 -> "Kids"
            9648 -> "Mystery"
            10763 -> "News"
            10764 -> "Reality"
            10765 -> "Sci-Fi & Fantasy"
            10766 -> "Soap"
            10767 -> "Talk"
            10768 -> "War & Politics"
            37 -> "Western"
            else -> "Unknown"
        }
    }
}