package com.example.cinemasurf.moviedetails

import android.util.Log
import com.example.cinemasurf.api.MoviesService
import com.example.cinemasurf.db.FavouriteMovie
import com.example.cinemasurf.db.FavouriteMovieDao
import io.swagger.client.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface MovieDetailsRepository {
    val movie: StateFlow<Movie>
    suspend fun movieIdGet(id: kotlin.Int, appendToResponse: kotlin.String)
    suspend fun favouriteMovieInsert()
    suspend fun favouriteMovieDelete()
}

class DefaultMovieDetailsRepository @Inject constructor(
    private val moviesService: MoviesService,
    private val favouriteMovieDao: FavouriteMovieDao
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
        Log.d("TEST", "get ${_movie.value}")
    }

    override suspend fun favouriteMovieInsert() {
        movie.collect{
            Log.d("TEST", "insert ${it.toString()}")
            favouriteMovieDao.insertFavouriteMovie(FavouriteMovie(it))
        }

    }

    override suspend fun favouriteMovieDelete() {
        movie.collect{
            Log.d("TEST", "delete ${it.toString()}")
            favouriteMovieDao.deleteFavouriteMovie(FavouriteMovie(it))
        }

    }

}