package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.FrontSimpleCallback
import kotlinx.coroutines.flow.StateFlow

interface SearchMediaListViewModel {
    val state: StateFlow<MediaListState>
    fun getSearchMediaByTitle(title: String, callback: FrontSimpleCallback?)
}