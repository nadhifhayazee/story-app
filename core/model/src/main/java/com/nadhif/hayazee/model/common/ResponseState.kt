package com.nadhif.hayazee.model.common

sealed class ResponseState<T> {
    class Loading<T> : ResponseState<T>()
    data class Success<T>(val data: T?) : ResponseState<T>()
    data class Error<T>(val message: String?) : ResponseState<T>()
}
