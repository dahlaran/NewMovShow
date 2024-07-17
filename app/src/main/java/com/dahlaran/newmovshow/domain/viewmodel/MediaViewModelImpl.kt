package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.data.MainEvents
import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


// TODO: Implement the refresh status and pagination
@HiltViewModel
class MediaViewModelImpl @Inject constructor(
    private val getMediasUseCase: GetMediasUseCase,
) : BaseViewModel(), MediaViewModel {

    private val _mainState = MutableStateFlow(MainState())
    override val mainState = _mainState.asStateFlow()

    override fun onEvent(event: MainEvents) {
        if (event is MainEvents.Refresh) {
            getMedias()
        }
    }

    /**
     * Get list of medias
     *
     * @return a flow of DataState<List<Media>>
     */
    private fun getMedias() {
        launchUsesCase(getMediasUseCase.invoke(_mainState.value.mediaPage),
            onLoading = { loadingStatus ->
                _mainState.update {
                    it.copy(isLoading = loadingStatus)
                }
            },
            onSuccess = { medias ->
                _mainState.update {
                    it.copy(
                        medias = (_mainState.value.medias + medias).toSet().toList(),
                        mediaPage = _mainState.value.mediaPage + 1
                    )
                }
            })
    }
}