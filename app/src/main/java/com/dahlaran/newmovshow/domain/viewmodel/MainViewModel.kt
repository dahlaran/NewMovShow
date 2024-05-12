package com.dahlaran.newmovshow.domain.viewmodel

import com.dahlaran.newmovshow.common.domain.BaseViewModel
import com.dahlaran.newmovshow.common.domain.FrontSimpleCallback
import com.dahlaran.newmovshow.domain.use_case.GetMediasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


// TODO: Implement the refresh status and pagination
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMediasUseCase: GetMediasUseCase,
): BaseViewModel() {
    init {
        getMedias(callback = null)
    }

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    /**
     * Get medias
     *
     * @param callback the callback to call when the search is done
     * @return a flow of DataState<List<Media>>
     */
    fun getMedias(callback: FrontSimpleCallback?) {
        launchUsesCase(getMediasUseCase.invoke(_mainState.value.mediaPage), callback,
            onLoading = { loadingStatus ->
                _mainState.update {
                    it.copy(
                        isLoading = loadingStatus
                    )
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