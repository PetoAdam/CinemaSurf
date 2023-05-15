package com.example.cinemasurf.moviedetails

import io.swagger.client.models.Movie


data class MovieDetailsUiState(val movie: Pair<Movie, Boolean>)