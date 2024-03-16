package com.dahlaran.newmovshow.domain.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallback
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import com.dahlaran.newmovshow.domain.use_case.SearchMediaByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val getMediasUseCase: GetMediasUseCase,
    private val searchMediasUseCase: SearchMediaByTitle
) : BaseViewModel() {

    var state by mutableStateOf(MediaListState())

    init {
        getMedias(0, null)
    }

    /**
     * Get medias
     *
     * @param page the page to get
     * @param callback the callback to call when the search is done
     * @return a flow of DataState<List<Media>>
     */
    fun getMedias(page: Int, callback: FrontSimpleCallback?) {
        launchUsesCase(getMediasUseCase.invoke(page), callback,
            onLoading = {
                state = state.copy(isLoading = it)
            },
            onSuccess = {
                state = state.copy(medias = it)
            })
    }

    /**
     * Search medias by title
     *
     * @param title the title of the media to search
     * @param callback the callback to call when the search is done
     * @return a flow of DataState<List<Media>>
     */
    fun getSearchMediaByTitle(title: String, callback: FrontSimpleCallback?) {
        state = state.copy(searchQuery = title)
        if (title.isEmpty()) {
            getMedias(0, callback)
        } else {
            launchUsesCase(searchMediasUseCase.invoke(title), callback,
                onLoading = {
                    state = state.copy(isLoading = it)
                },
                onSuccess = {
                    state = state.copy(medias = it)
                })
        }
    }
}