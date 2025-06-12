package com.dahlaran.newmovshow.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dahlaran.newmovshow.common.HtmlUtils


/**
 * Show is a data class that represents a show
 */
@Entity(tableName = "media")
data class Media(
    @PrimaryKey val id: String,
    val genres: List<String>?,
    val image: String?,
    val language: String?,
    val title: String,
    val officialSite: String?,
    val premiered: String?,
    val rating: Double,
    val runtime: Int,
    val seasons: SeasonList?,
    val status: String,
    val summary: String?,
    val type: String,
    val updated: Int,
    val url: String,
    val weight: Int,
    var isFavorite: Boolean = false,
    val video: VideoInfo? = null,
) {

    fun getSummaryToDisplay(): String {
        return HtmlUtils.convertHtmlTextToShowText(summary).toString().trim()
    }

    fun getDayBeforeNextEpisode(): Int {
        return seasons?.numberOfDayBeforeNextEpisode ?: 0
    }

    fun getGenresToDisplay(): String {
        return genres?.joinToString(", ") ?: ""
    }
}