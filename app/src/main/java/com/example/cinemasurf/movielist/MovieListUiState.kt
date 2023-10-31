package com.example.cinemasurf.movielist

import io.swagger.client.models.Movie


data class MovieListUiState(
    val movies: List<Pair<Movie, Boolean>>,
    val showFavourites: Boolean,
    val searchText: String
)