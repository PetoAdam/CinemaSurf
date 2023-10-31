package com.example.cinemasurf.api

import com.example.cinemasurf.api.ApiConstants.Companion.API_KEY
import io.swagger.client.apis.DefaultApi
import io.swagger.client.models.InlineResponse200
import io.swagger.client.models.Movie
import javax.inject.Inject

interface MoviesService {
    suspend fun searchMovies(query: String, page: Int): InlineResponse200
    suspend fun getTrendingMovies(): InlineResponse200
    suspend fun movieIdGet(id: kotlin.Int, appendToResponse: kotlin.String) : Movie
}

class DefaultMoviesService @Inject constructor(
    private val defaultApi: DefaultApi
) : MoviesService {
    override suspend fun searchMovies(query: String, page: Int): InlineResponse200 {
        return defaultApi.searchMovieGet(API_KEY, query, page)
    }
    override suspend fun getTrendingMovies(): InlineResponse200 {
        return defaultApi.trendingMovieDayGet(API_KEY)
    }

    override suspend fun movieIdGet(id: Int, appendToResponse: String): Movie {
        return defaultApi.movieIdGet(API_KEY, id, appendToResponse)
    }
}
