package com.example.cinemasurf.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.cinemasurf.api.MoviesService
import com.example.cinemasurf.db.FavouriteMovieDao
import com.example.cinemasurf.movielist.DefaultMovieListRepository
import com.example.cinemasurf.movielist.MovieListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(ConnectivityManager::class.java)

}