package com.example.cinemasurf.util

import androidx.compose.runtime.Composable
import com.example.cinemasurf.ui.LoadingIndicator
import com.example.cinemasurf.ui.NetworkErrorSurface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

inline fun <reified R : Any> Any?.asInstance() = this as? R

inline fun <reified T : Any> MutableStateFlow<in T>.updateIfInstance(function: (T) -> T): Boolean {
    while (true) {
        val prevValue = value.asInstance<T>() ?: return false
        val nextValue = function(prevValue)
        if (compareAndSet(prevValue, nextValue)) {
            return prevValue != nextValue
        }
    }
}

fun <T> MutableStateFlow<ViewState<T>>.updateIfSuccess(block: (T) -> T) =
    updateIfInstance<ViewState.Success<T>> {
        ViewState.Success(data = block(it.data))
    }

@Composable
fun <T> ContentWithBackgroundLoadingIndicator(state: ViewState<T>, onRetry: ()-> Unit, content: @Composable (T) -> Unit) {
    when (state) {
        is ViewState.Loading -> {
            LoadingIndicator()
        }
        is ViewState.Failure -> {
            NetworkErrorSurface(isOffline = state.isOffline) {
                onRetry()
            }
        }
        is ViewState.Success -> {
            content(state.data)
        }
    }
}


fun <T> MutableStateFlow<ViewState<T>>.valueAsSuccess() = this.value.asInstance<ViewState.Success<T>>()?.data

fun <T1, T2> Flow<T1>.combinePair(flow: Flow<T2>): Flow<Pair<T1, T2>> =
    combine(flow) { t1, t2 -> t1 to t2 }