package com.lloydsbankingassignment.data.service

sealed class ResponseCategory<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : ResponseCategory<T>(data = data)
    class Error<T>(message: String) : ResponseCategory<T>(message = message)
    class Loading<T> : ResponseCategory<T>()
}
