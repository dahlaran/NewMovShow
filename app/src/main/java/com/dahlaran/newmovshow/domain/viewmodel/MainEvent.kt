package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.Event

/**
 * Events for the Main State
 */
sealed class MainEvent : Event {
    data class Refresh(val text: String) : MainEvent()
    data class Search(val title: String) : MainEvent()
}