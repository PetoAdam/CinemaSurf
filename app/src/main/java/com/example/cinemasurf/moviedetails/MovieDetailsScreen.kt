package com.example.cinemasurf.moviedetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinemasurf.util.ViewState

class MovieDetailsScreen {

    @Composable
    fun MovieDetailsScreen(viewModel: MovieDetailsViewModel = hiltViewModel<DefaultMovieDetailsViewModel>()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val movieDetailsUiState by viewModel.movieDetailsUiState.collectAsState()
            MovieDetailsScreenContent(
                movieDetailsUiState = movieDetailsUiState
            )
        }
    }

    @Composable
    fun MovieDetailsScreenContent(
        movieDetailsUiState: ViewState<MovieDetailsUiState>
    ){
        // TODO: Add UI elements...
    }
}