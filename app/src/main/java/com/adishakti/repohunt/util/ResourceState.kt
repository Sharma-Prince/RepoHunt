package com.adishakti.repohunt.util

sealed class ResourceState<T> {
    class Loading<T> : ResourceState<T>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error<T>(val data: Any) : ResourceState<T>()
    class PaginationLoading<T> : ResourceState<T>()
}