package com.dahlaran.newmovshow.domain.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.GetMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(val detailUsesCase: GetMediaUseCase) : BaseViewModel() {

    var state by mutableStateOf(MediaDetailState())

    fun getMediaDetail(mediaId: String) {
        launchUsesCase(detailUsesCase.invoke(mediaId),
            onLoading = {
                state = state.copy(isLoading = it)
            },
            onSuccess = {
                state = state.copy(media = it)
            })
    }
}