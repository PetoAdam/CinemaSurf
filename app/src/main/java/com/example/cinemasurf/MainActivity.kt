package com.example.cinemasurf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinemasurf.moviedetails.MovieDetailsScreen
import com.example.cinemasurf.movielist.MovieDetails
import com.example.cinemasurf.movielist.MovieListScreen
import com.example.cinemasurf.ui.theme.CinemaSurfTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        setContent {
            CinemaSurfTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "movieList"
                ){
                    composable("movieList"){ MovieListScreen(navController = navController)}
                    composable("movieDetails/{movieId}/{isFavourite}",
                        arguments = listOf(
                            navArgument("movieId") { type = NavType.IntType},
                            navArgument("isFavourite"){type = NavType.BoolType})
                    ){ backStackEntry ->
                        val movieId = backStackEntry.arguments?.getInt("movieId")
                        val isFavourite = backStackEntry.arguments?.getBoolean("isFavourite")
                        MovieDetailsScreen(
                            navController = navController
                        )
                    }
                }


            }
        }
    }
}