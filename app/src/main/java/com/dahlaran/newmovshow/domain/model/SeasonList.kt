package com.dahlaran.newmovshow.domain.model

import org.joda.time.DateTime
import org.joda.time.Days
import java.util.Date

data class SeasonList(
    val seasons: List<Season>?,
    val lastEpisode: Episode?,
    val nextEpisode: Episode?
) {
    var numberOfDayBeforeNextEpisode: Int = -1
        get() {
            if (field == -1) {
                field = getDayBeforeNextEpisode()
            }
            return field
        }
        private set

    /**
     * Get the number of day before the next episode
     *
     * @return Number of day before the next episode
     */
    private fun getDayBeforeNextEpisode(): Int {
        val now = DateTime.now()
        val nextEpisodeDate: Date? = nextEpisode?.time

        if (nextEpisodeDate != null) {
            // Convert javaDate to jonaDate
            val jonaDateOfNextEpisode = DateTime(nextEpisodeDate)
            return Days.daysBetween(now, jonaDateOfNextEpisode).days
        }
        return 0
    }
}

