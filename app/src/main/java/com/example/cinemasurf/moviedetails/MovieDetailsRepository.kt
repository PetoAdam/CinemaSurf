package com.example.cinemasurf.moviedetails

import com.example.cinemasurf.api.MoviesService
import com.example.cinemasurf.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieDetailsRepository {
    val movie: StateFlow<Movie>

    // TODO: add possible function eg: fetchMovies...
}

class DefaultMovieDetailsRepository @Inject constructor(
    private val movieListService: MoviesService
) : MovieDetailsRepository{
    private val _movie = MutableStateFlow<Movie>(Movie(id = ""))

    override val movie: StateFlow<Movie>
        get() = _movie

    // TODO: Override all functions defined in the interface
}