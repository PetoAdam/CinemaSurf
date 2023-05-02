package com.example.cinemasurf.di

import com.example.cinemasurf.movielist.DefaultMovieListRepository
import com.example.cinemasurf.movielist.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindMovieListRepository(venuesRepository: DefaultMovieListRepository): MovieListRepository

}