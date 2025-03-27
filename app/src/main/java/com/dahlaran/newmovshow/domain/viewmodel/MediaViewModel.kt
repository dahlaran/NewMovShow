package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import com.dahlaran.newmovshow.domain.use_case.SearchMediaByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject


// TODO: Implement the refresh status and pagination
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val getMediasUseCase: GetMediasUseCase,
    private val searchMediaUseCase: SearchMediaByTitleUseCase,
) : BaseViewModel<MediaListState, MainEvent>(MediaListState()) {

    override fun onEvent(event: MainEvent) {
        if (event is MainEvent.Refresh) {
            getMedias()
        }
        else if (event is MainEvent.Search) {
            getSearchMediaByTitle(event.title)
        }
    }

    /**
     * Get list of medias
     *
     * @return a flow of DataState<List<Media>>
     */
    private fun getMedias() {
        launchUsesCase(getMediasUseCase.invoke(_state.value.mediaPage),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(isLoading = loadingStatus)
                }
            },
            onSuccess = { medias ->
                _state.update {
                    it.copy(
                        medias = (_state.value.medias + medias).toSet().toList(),
                        mediaPage = _state.value.mediaPage + 1
                    )
                }
            })
    }

    /**
     * Search medias by title
     *
     * @param title the title of the media to search
     * @return a flow of DataState<List<Media>>
     */
    private fun getSearchMediaByTitle(title: String) {
        if (_state.value.searchQuery != title) {
            _state.update {
                it.copy(
                    searchQuery = title,
                    medias = emptyList(),
                    mediaPage = 0
                )
            }
        }
        launchUsesCase(searchMediaUseCase.invoke(title, _state.value.mediaPage),
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
                        medias = (_state.value.medias + searchedMedias).toSet().toList(),
                        mediaPage = _state.value.mediaPage + 1
                    )
                }
            })
    }
}