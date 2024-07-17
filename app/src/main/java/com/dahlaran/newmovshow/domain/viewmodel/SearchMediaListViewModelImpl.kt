package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallback
import com.dahlaran.newmovshow.domain.use_case.SearchMediaByTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchMediaListViewModelImpl @Inject constructor(
    private val searchMediasUseCase: SearchMediaByTitle
) : BaseViewModel(), SearchMediaListViewModel {

    private val _state: MutableStateFlow<MediaListState> = MutableStateFlow(MediaListState())
    override val state: StateFlow<MediaListState> = _state.asStateFlow()


    /**
     * Search medias by title
     *
     * @param title the title of the media to search
     * @param callback the callback to call when the search is done
     * @return a flow of DataState<List<Media>>
     */
    override fun getSearchMediaByTitle(title: String, callback: FrontSimpleCallback?) {
        if (_state.value.searchQuery != title) {
            _state.update {
                it.copy(
                    searchQuery = title,
                    searchedMedias = emptyList(),
                )
            }
        }
        launchUsesCase(searchMediasUseCase.invoke(title, _state.value.searchPage),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(
                        isLoading = loadingStatus
                    )
                }
            },
            onSuccess = { searchedMedias ->
                if (title != _state.value.searchQuery) {
                    return@launchUsesCase
                }
                _state.update {
                    it.copy(
                        searchedMedias = (_state.value.searchedMedias + searchedMedias).toSet().toList(),
                        searchPage = _state.value.searchPage + 1
                    )
                }
            })
    }
}