package com.dahlaran.newmovshow.data.remote.data.tmdb

import com.dahlaran.newmovshow.domain.model.Media
import com.google.gson.annotations.SerializedName

data class TMDbMovieResponse(
    val page: Int,
    val results: List<TMDbMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDbTVResponse(
    val page: Int,
    val results: List<TMDbTVShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDbMultiSearchResponse(
    val page: Int,
    val results: List<TMDbSearchResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDbSearchResult(
    val id: Int,
    @SerializedName("media_type") val mediaType: String,
    val title: String?,
    val name: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    val popularity: Double
) {
    fun toMedia() : Media {
        return Media(
            id = id.toString(),
            genres = genreIds.map { it.toString() },
            image = "https://image.tmdb.org/t/p/w500$posterPath",
            language = originalLanguage,
            title = title ?: name ?: "Unknown",
            officialSite = null,
            premiered = releaseDate ?: firstAirDate,
            rating = voteAverage,
            runtime = 0,
            seasons = null,
            status = "Released",
            summary = overview,
            type = mediaType,
            updated = 0,
            url = "https://www.themoviedb.org/$mediaType/$id",
            weight = 0,
            isFavorite = false
        )
    }
}