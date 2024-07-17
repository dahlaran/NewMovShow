package com.dahlaran.newmovshow.common.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dahlaran.newmovshow.common.data.DataState
import com.dahlaran.newmovshow.common.data.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

    protected fun <T> launchUsesCase(
        functionToLaunch: Flow<DataState<T>>,
        onLoading: ((Boolean) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Error) -> Unit)? = null,
    ) {

        functionToLaunch.onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    runOnUIThread {
                        onLoading?.invoke(dataState.loading)
                    }
                }

                is DataState.Success -> {
                    runOnUIThread {
                        onSuccess?.invoke(dataState.data)
                    }
                }

                is DataState.Error -> {
                    runOnUIThread {
                        onError?.invoke(dataState.error)
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO + Job()))
    }


    protected fun runOnUIThread(block: () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }
}