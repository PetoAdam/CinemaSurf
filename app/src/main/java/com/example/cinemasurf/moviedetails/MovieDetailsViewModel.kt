package com.example.cinemasurf.moviedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasurf.db.FavouriteMovie
import com.example.cinemasurf.db.FavouriteMovieDao
import com.example.cinemasurf.movielist.MovieListUiState
import com.example.cinemasurf.util.ViewState
import com.example.cinemasurf.util.combinePair
import com.example.cinemasurf.util.updateIfSuccess
import com.example.cinemasurf.util.valueAsSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.swagger.client.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MovieDetailsViewModel {
    val movieDetailsUiState: StateFlow<ViewState<MovieDetailsUiState>>
    fun setFavourite(isFavourite: Boolean)
    fun onRetry()
}

@HiltViewModel
class DefaultMovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val saveStateHandle: SavedStateHandle,
    private val favouriteMovieDao: FavouriteMovieDao
) : ViewModel(), MovieDetailsViewModel {

    override val movieDetailsUiState =
        MutableStateFlow<ViewState<MovieDetailsUiState>>(ViewState.Loading)
    private val movieId: Int = checkNotNull(saveStateHandle["movieId"])
    override fun setFavourite(isFavourite: Boolean) {
        viewModelScope.launch {
            if (isFavourite)
                movieDetailsRepository.favouriteMovieInsert()
            else
                movieDetailsRepository.favouriteMovieDelete()
        }
    }

    private var fetchMovieJob: Job? = null

    init {
        launchFetchMovie()
        collectMovie()
    }

    override fun onRetry() {
        launchFetchMovie()
    }

    private fun launchFetchMovie() {
        fetchMovieJob?.cancel()
        fetchMovieJob = viewModelScope.launch(Dispatchers.IO) { fetchMovie() }
    }

    private suspend fun fetchMovie() {
        val state = movieDetailsUiState.valueAsSuccess()
        if (state?.movie?.second == true) {
            favouriteMovieDao.getFavouriteMovie(movieId)
        } else {
            movieDetailsRepository.movieIdGet(movieId, " ")
        }

    }

    private fun mapMovieDetailsToState(
        movie: Movie,
        isFavourite: Boolean
    ): MovieDetailsUiState {
        return MovieDetailsUiState(Pair<Movie, Boolean>(movie, isFavourite))
    }
    private fun collectMovie(){
        viewModelScope.launch {
            movieDetailsRepository.movie.combinePair(favouriteMovieDao.getFavouriteMovie(movieId)).collect { (movie, favouriteMovie) ->
                Log.d("TEST", "moviesCollect $movie")
                movieDetailsUiState.update {
                    ViewState.Success(mapMovieDetailsToState(movie, favouriteMovie != null))
                }
            }
        }
    }
}