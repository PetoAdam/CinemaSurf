package com.example.cinemasurf.movielist

import com.example.cinemasurf.api.MoviesService
import io.swagger.client.apis.DefaultApi
import io.swagger.client.models.InlineResponse200
import io.swagger.client.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieListRepository {

    val movies: StateFlow<List<Movie>>
    suspend fun searchMovieGet(query: kotlin.String, page: kotlin.Int)
    suspend fun trendingMovieDayGet()

}

class DefaultMovieListRepository @Inject constructor(
    private val moviesService: MoviesService
) : MovieListRepository{
    override val movies = MutableStateFlow<List<Movie>>(emptyList())

    override suspend fun searchMovieGet(query: String, page: Int) {
        val response = moviesService.searchMovies(query, page)
        movies.value = response.results?.toList() ?: emptyList()
    }

    override suspend fun trendingMovieDayGet() {
        val response = moviesService.getTrendingMovies()
        movies.value = response.results?.toList() ?: emptyList()
    }
}