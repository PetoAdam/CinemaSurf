package com.example.cinemasurf.movielist

import androidx.lifecycle.ViewModel
import com.example.cinemasurf.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieListViewModel{
    val movieListUiState: StateFlow<ViewState<MovieListUiState>>
}

@HiltViewModel
class DefaultMovieListViewModel @Inject constructor(

) : ViewModel(), MovieListViewModel {

    override val movieListUiState = MutableStateFlow<ViewState<MovieListUiState>>(ViewState.Loading)
}