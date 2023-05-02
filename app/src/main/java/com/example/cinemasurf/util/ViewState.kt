package com.example.cinemasurf.util

sealed interface ViewState<out T> where T : Any? {

    object Loading : ViewState<Nothing>

    data class Success<T>(val data: T) : ViewState<T>

    data class Failure(val isOffline: Boolean) : ViewState<Nothing>

}