import com.example.cinemasurf.db.FavouriteMovie
import com.example.cinemasurf.db.FavouriteMovieDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavouriteMovieDaoTest {
    private lateinit var favouriteMovieDao: FavouriteMovieDao

    @Before
    fun setup() {
        favouriteMovieDao = mockk(relaxed = true)
    }

    @Test
    fun testGetAllFavouriteMovies() = runBlocking {
        // Arrange
        val movies = listOf(FavouriteMovie(1, "Movie 1", null, null, null, null,null, null, null, null), FavouriteMovie(2, "Movie 2",null, null, null, null,null, null, null, null))
        coEvery { favouriteMovieDao.getAllFavouriteMovies() } returns flowOf(movies)

        // Act
        val result = favouriteMovieDao.getAllFavouriteMovies().first()

        // Assert
        assertEquals(movies, result)
    }

    @Test
    fun testGetFavouriteMovie() = runBlocking {
        // Arrange
        val movie = FavouriteMovie(1, "Movie 1",null, null, null, null,null, null, null, null)
        coEvery { favouriteMovieDao.getFavouriteMovie(movie.id) } returns flowOf(movie)

        // Act
        val result = favouriteMovieDao.getFavouriteMovie(movie.id).first()

        // Assert
        assertEquals(movie, result)
    }

    @Test
    fun testInsertFavouriteMovie() = runBlocking {
        // Arrange
        val movie = FavouriteMovie(1, "Movie 1", null, null, null, null,null, null, null, null)

        // Act
        favouriteMovieDao.insertFavouriteMovie(movie)

        // Assert
        coEvery { favouriteMovieDao.getFavouriteMovie(movie.id) } returns flowOf(movie)
        val result = favouriteMovieDao.getFavouriteMovie(movie.id).first()
        assertEquals(movie, result)
    }

    @Test
    fun testDeleteFavouriteMovie() = runBlocking {
        // Arrange
        val movie = FavouriteMovie(1, "Movie 1",null, null, null, null,null, null, null, null)

        // Act
        favouriteMovieDao.deleteFavouriteMovie(movie)

        // Assert
        coEvery { favouriteMovieDao.getFavouriteMovie(movie.id) } returns flowOf(null)
        val result = favouriteMovieDao.getFavouriteMovie(movie.id).first()
        assertEquals(null, result)
    }
}
