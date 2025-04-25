package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.GetFavoritesMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MediaFavoriteViewModel @Inject constructor(
    private val getFavoritesMediaUseCase: GetFavoritesMediaUseCase,
) : BaseViewModel<MediaFavoriteState, MediaFavoriteEvent>(MediaFavoriteState()) {
    override fun onEvent(event: MediaFavoriteEvent) {
        when (event) {
            is MediaFavoriteEvent.ArriveOnFavorite -> {
                getFavoriteMedia()
            }

            MediaFavoriteEvent.Refresh -> {
                getFavoriteMedia()
            }
        }
    }


    private fun getFavoriteMedia() {
        launchUsesCase(
            getFavoritesMediaUseCase.invoke(),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(isLoading = loadingStatus, error = null)
                }
            },
            onSuccess = { medias ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        favoriteMedias = medias
                    )
                }
            },
            onError = { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
                }
            })
    }
}