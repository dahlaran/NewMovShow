package com.dahlaran.newmovshow.domain.model

import android.text.Spanned
import com.dahlaran.newmovshow.common.DateUtils
import com.dahlaran.newmovshow.common.HtmlUtils
import java.util.Date

data class Episode(
    val airdate: String,
    val airstamp: String,
    val airtime: String,
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val number: Int,
    val runtime: Int,
    val season: Int,
    val summary: String?,
    var time: Date?,
    val url: String?
) {
    private var summarySpanned: Spanned? = null


    /**
     * Make a string with the season and the number of the episode
     *
     * @return String with the season and the number of the episode
     */
    fun makeEpisodeDetail(): String {
        return "${season}x${number}"
    }

    /**
     * Init the time of apparition of the episode
     */
    fun initTimeOfApparition() {
        time = DateUtils.getEpisodeDateTime(airstamp)
    }

    fun makeEpisodeDateText(): String {
        return time?.run {
            String.format("%02d", DateUtils.getYearFromDate(this)) + "-" +
                    String.format("%02d", DateUtils.getMonthFromDate(this)) + "-" +
                    String.format("%02d", DateUtils.getDayOfWeekFromDate(this))
        } ?: ""
    }

    fun getTextSummary(): String {
        if (summarySpanned == null) {
            summarySpanned = HtmlUtils.convertHtmlTextToShowText(summary)
        }
        if (summarySpanned?.isNotEmpty() == true) {
            return summarySpanned.toString().trim()
        }
        return summarySpanned.toString()
            .trim() //NewMovShowApplication.instance.get()?.getString(R.string.episode_any_summary) ?: ""
    }
}