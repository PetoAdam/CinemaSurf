package com.example.cinemasurf.movielist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinemasurf.util.ViewState

class MovieListScreen {

    @Composable
    fun MovieListScreen(viewModel: MovieListViewModel = hiltViewModel<DefaultMovieListViewModel>()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val movieListUiState by viewModel.movieListUiState.collectAsState()
            MovieListScreenContent(
                movieListUiState = movieListUiState
            )
        }
    }

    @Composable
    fun MovieListScreenContent(
        movieListUiState: ViewState<MovieListUiState>
    ){
        // TODO: Add UI elements...
    }
}