package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.Event

/**
 * Events for the Detail State
 */
sealed class DetailEvent: Event {

    data class ArriveOnMedia(val mediaId: String): DetailEvent()
    data object Refresh : DetailEvent()
    data object AddFavorite : DetailEvent()
    data object RemoveFavorite : DetailEvent()
}