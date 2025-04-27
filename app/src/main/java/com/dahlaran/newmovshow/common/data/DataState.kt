package com.dahlaran.newmovshow.common.data


/**
 * DataState is a sealed class that can be used to handle the state of a data
 * It can be used to handle the state of a data in a ViewModel
 */
sealed class DataState<out T> {
    /**
     * Loading state
     */
    data class Loading<T>(val isLoading: Boolean = true) : DataState<T>()

    /**
     * Success state
     *
     * @param data the data that was loaded
     */
    data class Success<out T>(val data: T) : DataState<T>()

    /**
     * Error state
     *
     * @param error the error that occurred
     */
    data class Error(val error: com.dahlaran.newmovshow.common.data.Error) : DataState<Nothing>()
}