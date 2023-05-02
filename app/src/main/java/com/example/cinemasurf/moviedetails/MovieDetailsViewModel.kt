package com.example.cinemasurf.moviedetails

import androidx.lifecycle.ViewModel
import com.example.cinemasurf.movielist.MovieListUiState
import com.example.cinemasurf.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieDetailsViewModel{
    val movieDetailsUiState: StateFlow<ViewState<MovieDetailsUiState>>
}

@HiltViewModel
class DefaultMovieDetailsViewModel @Inject constructor(

) : ViewModel(), MovieDetailsViewModel {

    override val movieDetailsUiState = MutableStateFlow<ViewState<MovieDetailsUiState>>(ViewState.Loading)
}