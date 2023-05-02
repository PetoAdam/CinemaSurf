package com.example.cinemasurf.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemasurf.db.FavouriteMovie
import com.example.cinemasurf.db.FavouriteMovieDao

// Database for the favourite movies
@Database(entities = [FavouriteMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}