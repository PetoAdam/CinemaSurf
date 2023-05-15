package com.example.cinemasurf.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {
    @Query("SELECT * FROM favouriteMovies")
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteMovie(movie: FavouriteMovie)

    @Delete
    suspend fun deleteFavouriteMovie(id: Int): FavouriteMovie?

    // Add other methods as needed
}