package com.lloydsdata.service

sealed class DataLoadingState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : DataLoadingState<T>(data = data)
    class Error<T>(message: String) : DataLoadingState<T>(message = message)
    class Loading<T> : DataLoadingState<T>()
}
