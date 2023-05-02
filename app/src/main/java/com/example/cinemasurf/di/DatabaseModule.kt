package com.example.cinemasurf.di

import android.content.Context
import androidx.room.Room
import com.example.cinemasurf.db.FavouriteMovieDao
import com.example.cinemasurf.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideFavouriteMovieDao(appDatabase: AppDatabase): FavouriteMovieDao {
        return appDatabase.favouriteMovieDao()
    }

}