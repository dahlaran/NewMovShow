package com.dahlaran.newmovshow.common.data

/**
 * Events for the Main State
 */
sealed class MainEvents {
    data class Refresh(val text: String) : MainEvents()
    data class OnPaginate(val type: String) : MainEvents()
}