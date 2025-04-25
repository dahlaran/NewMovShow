package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.Event


/**
 * Events for the Detail State
 */
sealed class MediaFavoriteEvent : Event {
    data object ArriveOnFavorite : MediaFavoriteEvent()
    data object Refresh : MediaFavoriteEvent()
}