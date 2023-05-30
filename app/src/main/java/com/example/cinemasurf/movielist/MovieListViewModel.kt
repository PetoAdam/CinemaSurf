package com.example.cinemasurf.movielist

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemasurf.api.DefaultMoviesService
import com.example.cinemasurf.api.MoviesService
import com.example.cinemasurf.db.FavouriteMovie
import com.example.cinemasurf.db.FavouriteMovieDao
import com.example.cinemasurf.util.ViewState
import com.example.cinemasurf.util.asInstance
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MovieListViewModel {
    val movieListUiState: StateFlow<ViewState<MovieListUiState>>
    fun onFavouriteIconClicked()

    fun onSearchTextChanged(text: String)
    fun onRetry()
}

@HiltViewModel
class DefaultMovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val favouriteMovieDao: FavouriteMovieDao
) : ViewModel(), MovieListViewModel {

    override val movieListUiState = MutableStateFlow<ViewState<MovieListUiState>>(ViewState.Loading)

    private var fetchMoviesJob: Job? = null

    init {
        launchFetchMovies()
        collectMovies()
    }

    override fun onFavouriteIconClicked() {
        movieListUiState.updateIfSuccess {
            it.copy(showFavourites = !it.showFavourites)
        }
        launchFetchMovies()
    }

    override fun onSearchTextChanged(text: String) {
        movieListUiState.updateIfSuccess {
            it.copy(searchText = text)
        }
        launchFetchMovies()
    }

    override fun onRetry() {
        launchFetchMovies()
    }

    private fun launchFetchMovies() {
        fetchMoviesJob?.cancel()
        fetchMoviesJob = viewModelScope.launch(Dispatchers.Default) { fetchMovies() }
    }

    private suspend fun fetchMovies() {

        val state = movieListUiState.valueAsSuccess()
        if (state?.showFavourites != true) {
            // Fetch all movies and favourites too
            if ((state?.searchText ?: "") == "")
                movieListRepository.trendingMovieDayGet()
            else
                movieListRepository.searchMovieGet(state?.searchText!!, 1)
        }

    }

    private fun collectMovies() {
        viewModelScope.launch {

            combine(movieListRepository.movies, favouriteMovieDao.getAllFavouriteMovies(), movieListUiState){ movies, favouriteMovies, state ->
                val uiState = state.asInstance<ViewState.Success<MovieListUiState>>()?.data
                movieListUiState.update {
                    ViewState.Success(
                        mapMovieListsToState(
                            movies,
                            favouriteMovies.filter{ it.title?.uppercase()?.contains(uiState?.searchText?.uppercase() ?: "") ?: true },
                            uiState?.showFavourites ?: false, // Show trending movies on default, not favourites
                            uiState?.searchText ?: "" // Set Search text to empty
                        )
                    )
                }
            }.collect()
        }
    }

    private fun mapMovieListsToState(
        movies: List<Movie>,
        favourites: List<FavouriteMovie>,
        isFavourite: Boolean,
        searchText: String
    ): MovieListUiState {
        if (isFavourite) {
            val favouriteIds = favourites.map { it.id }
            val favouriteMovies = favourites.filter { it.id in favouriteIds }
            return MovieListUiState(
                favouriteMovies.map { movie -> Movie(movie) to true },
                isFavourite,
                searchText
            )
        } else {
            return MovieListUiState(movies.map { movie -> movie to false }, isFavourite, searchText)
        }
    }

}