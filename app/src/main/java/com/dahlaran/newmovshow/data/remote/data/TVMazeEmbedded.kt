package com.dahlaran.newmovshow.data.remote.data

import com.dahlaran.newmovshow.domain.model.Episode
import com.dahlaran.newmovshow.domain.model.Season
import com.dahlaran.newmovshow.domain.model.SeasonList
import java.util.Calendar

data class TVMazeEmbedded(
    val episodes: List<TVMazeEpisode>?
) {
    /**
     * Convert list of episodes to SeasonList
     *
     * @return new SeasonList entity
     */
    fun toSeasonList(): SeasonList? {
        val newEpisodes = episodes?.map { it.toEpisode() } ?: return null
        val listOfSeason = ArrayList<Season>()
        var tmpListOfEpisode = ArrayList<Episode>()
        var currentSeason = -1
        newEpisodes.forEach {
            if (currentSeason == -1) {
                currentSeason = it.season
            }
            if (it.season != currentSeason) {
                if (tmpListOfEpisode.isNotEmpty()) {
                    listOfSeason.add(Season(currentSeason, tmpListOfEpisode))
                }
                ++currentSeason
                tmpListOfEpisode = ArrayList()
            }
            tmpListOfEpisode.add(it)
        }
        if (tmpListOfEpisode.isNotEmpty()) {
            listOfSeason.add(Season(currentSeason, tmpListOfEpisode))
        }

        val now = Calendar.getInstance().time

        // Initialize apparition date
        newEpisodes.map { if (it.time == null) it.initTimeOfApparition() }

        val latestEpisode = newEpisodes.lastOrNull { it.time != null && it.time!! <= now }
        val nextEpisode = newEpisodes.firstOrNull { it.time != null && it.time!! > now }

        return SeasonList(listOfSeason, latestEpisode, nextEpisode)
    }
}