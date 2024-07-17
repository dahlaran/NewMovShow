package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.data.MainEvents
import kotlinx.coroutines.flow.StateFlow


interface MediaViewModel {
    val mainState: StateFlow<MainState>
    fun onEvent(event: MainEvents)
}