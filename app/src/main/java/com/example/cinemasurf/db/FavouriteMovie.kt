package com.example.cinemasurf.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.swagger.client.models.Movie

@Entity(tableName = "favouriteMovies")
data class FavouriteMovie (
    @PrimaryKey val id: Int,
    val title: String?,
    val homepage: String?,
    val overview: String?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val vote_average: Double?,
    val vote_count: Int?
) {
    constructor(movie: Movie) : this(
        movie.id ?: 0,
        movie.title,
        movie.homepage,
        movie.overview,
        movie.poster_path,
        movie.release_date?.toString(),
        movie.revenue,
        movie.runtime,
        movie.vote_average?.toDouble(),
        movie.vote_count
    )
}