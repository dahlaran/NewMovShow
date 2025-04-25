package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.AddFavoriteMediaUseCase
import com.dahlaran.newmovshow.domain.use_case.GetMediaUseCase
import com.dahlaran.newmovshow.domain.use_case.RemoveFavoriteMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val detailUsesCase: GetMediaUseCase,
    private val addFavoriteMediaUseCase: AddFavoriteMediaUseCase,
    private val removeFavoriteMediaUseCase: RemoveFavoriteMediaUseCase
) :
    BaseViewModel<MediaDetailState, DetailEvent>(MediaDetailState()) {
    private var lastMediaId: String = ""

    override fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ArriveOnMedia -> {
                getMediaDetail(event.mediaId)
            }

            is DetailEvent.Refresh -> {
                getMediaDetail(lastMediaId)
            }

            is DetailEvent.AddFavorite -> {
                if (_state.value.media == null) return
                addFavoriteMedia(lastMediaId)
            }

            is DetailEvent.RemoveFavorite -> {
                if (_state.value.media == null) return
                removeFavoriteMedia(lastMediaId)
            }
        }
    }

    private fun getMediaDetail(mediaId: String) {
        lastMediaId = mediaId
        launchUsesCase(
            detailUsesCase.invoke(mediaId),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(isLoading = loadingStatus)
                }
            },
            onSuccess = { media ->
                _state.update {
                    it.copy(media = media)
                }
            })
    }

    private fun addFavoriteMedia(mediaId: String) {
        launchUsesCase(
            addFavoriteMediaUseCase.invoke(mediaId),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(isLoading = loadingStatus, error = null)
                }
            },
            onSuccess = { media ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        media = media
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

    private fun removeFavoriteMedia(mediaId: String) {
        launchUsesCase(
            removeFavoriteMediaUseCase.invoke(mediaId),
            onLoading = { loadingStatus ->
                _state.update {
                    it.copy(isLoading = loadingStatus, error = null)
                }
            },
            onSuccess = { media ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        media = media
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
            }
        )
    }
}