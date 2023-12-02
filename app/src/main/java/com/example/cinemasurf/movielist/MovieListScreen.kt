package com.example.cinemasurf.movielist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cinemasurf.util.ContentWithBackgroundLoadingIndicator
import com.example.cinemasurf.util.ViewState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import io.swagger.client.models.Movie
import java.text.SimpleDateFormat


@Composable
fun MovieListScreen(
    viewModel: MovieListViewModel = hiltViewModel<DefaultMovieListViewModel>(),
    navController: NavController
) {
    val movieListUiState by viewModel.movieListUiState.collectAsState()
    MovieListScreenContent(
        navController = navController,
        movieListUiState = movieListUiState,
        onRetry = viewModel::onRetry,
        onSearchTextChanged = viewModel::onSearchTextChanged,
        onFavouriteIconClicked = viewModel::onFavouriteIconClicked,
    )

}

@Composable
fun MovieListScreenContent(
    navController: NavController,
    movieListUiState: ViewState<MovieListUiState>,
    onRetry: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onFavouriteIconClicked: () -> Unit
) {
    ContentWithBackgroundLoadingIndicator(state = movieListUiState, onRetry = onRetry) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            )
        {
            MovieListHeader(
                isFavourite = state.showFavourites,
                searchText = state.searchText,
                onSearchTextChanged = onSearchTextChanged,
                onFavouriteIconClicked = onFavouriteIconClicked
            )
            MovieList(
                navController = navController,
                movieListUiState = state,
            )
        }


    }
}

@Composable
fun MovieListHeader(
    isFavourite: Boolean,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onFavouriteIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val searchWeight = if (searchText == "") 1f else 5f
        // Search Bar
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = onSearchTextChanged,
            modifier = Modifier
                .weight(searchWeight)
                .height(60.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        if (searchText == "") {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(16.dp))

        }

        // Two-Way Slider
        TwoWaySlider(
            isChecked = isFavourite,
            onCheckedChange = onFavouriteIconClicked,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .height(60.dp)
        )
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // You can customize the appearance and behavior of the search bar here
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = { Text("Search") },
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .height(36.dp)
            .background(MaterialTheme.colorScheme.background),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        },
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun TwoWaySlider(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onCheckedChange,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Filled.Favorite else Icons.TwoTone.Favorite,
            contentDescription = null,
            tint = if (isChecked) Color.Red else MaterialTheme.colorScheme.onBackground // Set red tint for filled version
        )
    }
}

@Composable
fun MovieList(
    navController: NavController,
    movieListUiState: MovieListUiState?
) {
    movieListUiState?.let {
        val listState = rememberLazyListState()
        AnimatedVisibility(movieListUiState.searchText != "") {
            Text(
                text = "Showing results for: " + movieListUiState.searchText,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)

            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("MOVIE_LIST_TAG"),
            state = listState
        ) {
            items(it.movies) { movie ->
                MovieListItem(
                    movie = movie.first,
                    modifier = Modifier.clickable { navController.navigate("movieDetails/${movie.first.id}/${movie.second}") }
                )
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: Movie,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .testTag(movie.id.toString()),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        movie.poster_path?.let { MovieImage(url = "https://image.tmdb.org/t/p/original$it") }
        MovieDetails(
            movie = movie,
            modifier = Modifier.weight(1f)
        )
    }
    Divider(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    )
}

@Composable
fun MovieImage(url: String) {

    Box(
        modifier = Modifier
            .size(100.dp) // Adjust the size as per your requirements
    ) {
        GlideImage(
            imageModel = { url },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            ), // Provide an appropriate description if needed
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier.background(
                        brush = Brush.linearGradient(
                            listOf(Gray, Color.White)
                        )
                    )
                )
            }

        )
    }
}

@Composable
fun MovieDetails(movie: Movie, modifier: Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = movie.title ?: "Unknown title",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 2.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text =
            if (movie.release_date != null)
                SimpleDateFormat("dd/MM/yyyy").format(movie.release_date)
            else
                "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Rating: " + movie.vote_average,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}




