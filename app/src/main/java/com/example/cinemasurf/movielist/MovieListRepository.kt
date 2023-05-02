package com.example.cinemasurf.movielist

import com.example.cinemasurf.api.MoviesService
import com.example.cinemasurf.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieListRepository {
    val movies: StateFlow<List<Movie>>

    // TODO: add possible function eg: fetchMovies...
}

class DefaultMovieListRepository @Inject constructor(
    private val moviesService: MoviesService
) : MovieListRepository{
    override val movies = MutableStateFlow<List<Movie>>(emptyList())

    // TODO: Override all functions defined in the interface
}