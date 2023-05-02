package com.example.cinemasurf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cinemasurf.movielist.MovieListScreen
import com.example.cinemasurf.ui.theme.CinemaSurfTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaSurfTheme {
                MovieListScreen()
            }
        }
    }
}