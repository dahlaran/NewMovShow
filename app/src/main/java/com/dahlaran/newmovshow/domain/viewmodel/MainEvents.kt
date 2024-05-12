package com.dahlaran.newmovshow.domain.viewmodel

/**
 * Events for the Main State
 */
sealed class MainEvents {
    data class Refresh(val type: String) : MainEvents()
    data class OnPaginate(val type: String) : MainEvents()
}