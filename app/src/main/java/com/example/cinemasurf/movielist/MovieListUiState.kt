package com.example.cinemasurf.movielist

import com.example.cinemasurf.model.Movie

data class MovieListUiState(val movies: List<Pair<Movie, Boolean>>)