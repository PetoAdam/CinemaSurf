package com.example.cinemasurf.moviedetails

import com.example.cinemasurf.api.MoviesService
import io.swagger.client.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieDetailsRepository {
    val movie: StateFlow<Movie>
    suspend fun movieIdGet(id: kotlin.Int, appendToResponse: kotlin.String)
}

class DefaultMovieDetailsRepository @Inject constructor(
    private val moviesService: MoviesService
) : MovieDetailsRepository{
    private val _movie = MutableStateFlow<Movie>(Movie(id = 0))

    override val movie: StateFlow<Movie>
        get() = _movie

    override suspend fun movieIdGet(
        id: Int,
        appendToResponse: String
    ) {
        val response = moviesService.movieIdGet(id, appendToResponse)
        _movie.value = response
    }
}