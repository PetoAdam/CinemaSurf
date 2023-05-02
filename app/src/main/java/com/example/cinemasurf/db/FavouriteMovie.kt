package com.example.cinemasurf.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteMovies")
data class FavouriteMovie (@PrimaryKey val id: String)