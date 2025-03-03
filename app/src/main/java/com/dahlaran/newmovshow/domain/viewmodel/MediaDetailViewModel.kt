package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.GetMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(private val detailUsesCase: GetMediaUseCase) :
    BaseViewModel<MediaDetailState, DetailEvent>(MediaDetailState()) {

    override fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.ArriveOnMedia -> {
                getMediaDetail(event.mediaId)
            }
        }
    }

    private fun getMediaDetail(mediaId: String) {
        launchUsesCase(detailUsesCase.invoke(mediaId),
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
}