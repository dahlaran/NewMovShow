package com.dahlaran.newmovshow.common.data

import com.dahlaran.newmovshow.common.Event

/**
 * Events for the Main State
 */
sealed class MainEvents : Event {
    data class Refresh(val text: String) : MainEvents()
    data class Search(val title: String) : MainEvents()
}