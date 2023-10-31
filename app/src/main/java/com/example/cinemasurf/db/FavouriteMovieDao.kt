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

    @Query("SELECT * FROM favouriteMovies WHERE id = :id")
    fun getFavouriteMovie(id: Int): Flow<FavouriteMovie?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteMovie(movie: FavouriteMovie)

    @Delete
    suspend fun deleteFavouriteMovie(movie: FavouriteMovie)

    // Add other methods as needed
}