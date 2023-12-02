package com.example.cinemasurf.moviedetails

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cinemasurf.ui.theme.CinemaSurfTheme
import com.example.cinemasurf.util.ContentWithBackgroundLoadingIndicator
import com.example.cinemasurf.util.ViewState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.swagger.client.models.Movie
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel<DefaultMovieDetailsViewModel>(),
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val movieDetailsUiState by viewModel.movieDetailsUiState.collectAsState()
        MovieDetailsScreenContent(
            navController = navController,
            movieDetailsUiState = movieDetailsUiState,
            onFavouritePressed = viewModel::setFavourite,
            onRetry = viewModel::onRetry,
        )
    }
}

@Composable
fun MovieDetailsScreenContent(
    navController: NavController,
    movieDetailsUiState: ViewState<MovieDetailsUiState>,
    onFavouritePressed: (Boolean) -> Unit,
    onRetry: () -> Unit
) {

    ContentWithBackgroundLoadingIndicator(state = movieDetailsUiState, onRetry = onRetry) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val movie = state.movie
            MovieDetailsHeader(
                navController = navController,
                title = movie.first.title ?: "Unknown title"
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                ImageSection(movie, onFavouritePressed, movie.second)
                DetailsSection(movie.first)
            }
        }
    }
}


@Composable
fun MovieDetailsHeader(navController: NavController, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
fun ImageSection(
    movie: Pair<Movie, Boolean>,
    onFavouritePressed: (Boolean) -> Unit,
    isFavourite: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        GlideImage(
            imageModel = { "https://image.tmdb.org/t/p/original${movie.first.poster_path}" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            ), // Provide an appropriate description if needed
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier.background(
                        brush = Brush.linearGradient(
                            listOf(Color.Gray, Color.White)
                        )
                    )
                )
            }

        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.TopStart)
                .clip(shape = RoundedCornerShape(8.dp))
                .fillMaxWidth(3f / 7f)
                .background(Color(0f, 0f, 0f, 0.8f)), // Semi-transparent background
        ) {
            Icon(
                imageVector = Icons.TwoTone.Star,
                contentDescription = "Rating",
                tint = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 10.dp)
            )
            val roundedRating = "%.2f".format(movie.first.vote_average)
            Text(
                text = roundedRating.toString() ?: "Unknown",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(2f),
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(Color(0f, 0f, 0f, 0.8f)) // Semi-transparent background

        ) {
            Text(
                text = movie.first.title.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .width(300.dp)
                    .weight(4f)

            )
            Spacer(Modifier.weight(0.5f))
            Log.d("TEST", "IsFavourite $isFavourite")
            IconButton(
                onClick = { onFavouritePressed(!isFavourite) },
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = if (isFavourite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.TwoTone.Favorite
                    },
                    tint = Color.White,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Composable
fun DetailsSection(movie: Movie?) {
    // Description
    Text(
        text = movie?.overview.orEmpty(),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    Divider(modifier = Modifier.height(2.dp))

    // Other details
    val revenue = movie?.revenue ?: 0
    val revenueString = NumberFormat.getNumberInstance(Locale.US).format(revenue / 1000000)
    val releaseDate = movie?.release_date?.let {
        SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(it)
    } ?: ""

    DetailRow(
        firstItem = {
            Text(
                text = "Release date:",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        secondItem = {
            Text(
                text = "$releaseDate",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )

    Divider()

    DetailRow(
        firstItem = {
            Text(
                text = "Runtime:",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        secondItem = {
            Text(
                text = "${movie?.runtime} minutes",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )

    Divider()

    DetailRow(
        firstItem = {
            Text(
                text = "Revenue:",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        secondItem = {
            Text(
                text = "$$revenueString million",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )

    Divider()

    DetailRow(
        firstItem = {
            Text(
                text = "Website:",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        secondItem = {
            HyperlinkText(
                url = movie?.homepage.orEmpty(),
                title = movie?.title.orEmpty(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    )

    Divider()

    DetailRow(
        firstItem = {
            Text(
                text = "Number of Ratings:",
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        secondItem = {
            Text(
                text = "${movie?.vote_count}",
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )
}

@Composable
fun DetailRow(
    firstItem: @Composable () -> Unit,
    secondItem: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    verticalPadding: Dp = 4.dp,
    horizontalPadding: Dp = 16.dp
) {
    Row(
        modifier = modifier.padding(vertical = verticalPadding, horizontal = horizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            firstItem()
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            secondItem()
        }
    }
}


fun openWebsiteUrl(url: String?, uriHandler: UriHandler) {
    if (url != null) {
        uriHandler.openUri(url)
    }
}

@Composable
fun HyperlinkText(url: String, title: String, modifier: Modifier) {
    val uriHandler = LocalUriHandler.current
    Text(
        text = title,
        modifier = modifier
            .clickable { openWebsiteUrl(url = url, uriHandler = uriHandler) },
        textDecoration = TextDecoration.Underline
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    CinemaSurfTheme {
        Surface(
            color = Color.White,
        ) {
            MovieDetailsScreenContent(
                navController = rememberNavController(),
                movieDetailsUiState = ViewState.Success(
                    MovieDetailsUiState(
                        Pair<Movie, Boolean>(
                            Movie(
                                385687,
                                "Movie Title",
                                null,
                                "Description",
                                "/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
                                null,
                                null,
                                null,
                                null,
                                null
                            ), false
                        )
                    )
                ),
                onFavouritePressed = {}) {

            }
        }
    }
}