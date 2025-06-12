package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.Media
import com.google.gson.annotations.SerializedName

/**
 * [TMDbMovie] is a data class representing a movie from The Movie Database (TMDb) API.
 * This class is used to map the JSON response from the TMDb API to a Kotlin data structure.
 *
 * @property id The unique identifier for the movie.
 * @property title The title of the movie.
 * @property overview A brief description of the movie.
 * @property posterPath The path to the movie's poster image.
 * @property backdropPath The path to the movie's backdrop image.
 * @property releaseDate The release date of the movie.
 * @property voteAverage The average rating of the movie.
 * @property voteCount The number of votes the movie has received.
 * @property genreIds A list of genre IDs associated with the movie.
 * @property adult Indicates whether the movie is intended for adults.
 * @property originalLanguage The original language of the movie.
 * @property originalTitle The original title of the movie.
 * @property popularity The popularity score of the movie.
 * @property videos A response containing video information related to the movie.
 */
data class TMDbMovie(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val genres: List<TMDbGenre>,
    val adult: Boolean,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val popularity: Double,
    val videos: TMDbVideosResponse?
) {

    /**
     * Converts the TMDbMovie instance to a Media object.
     *
     * @return A Media object containing the movie's details.
     */
    fun toMedia(): Media {
        return Media(
            id = "$id",
            genres = genres.map { it.name },
            image = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
            language = originalLanguage,
            title = title,
            officialSite = null,
            premiered = releaseDate,
            rating = voteAverage,
            runtime = 0,
            seasons = null,
            status = "Released",
            summary = overview,
            type = "Movie",
            updated = 0,
            url = "https://www.themoviedb.org/movie/$id",
            weight = popularity.toInt(),
            video = videos?.getBestTrailer()
        )
    }
}