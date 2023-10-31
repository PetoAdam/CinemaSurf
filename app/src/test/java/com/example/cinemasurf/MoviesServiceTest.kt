import com.example.cinemasurf.api.DefaultMoviesService
import com.example.cinemasurf.api.MoviesService
import io.mockk.coEvery
import io.mockk.mockk
import io.swagger.client.apis.DefaultApi
import io.swagger.client.models.InlineResponse200
import io.swagger.client.models.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DefaultMoviesServiceTest {
    private lateinit var moviesService: MoviesService
    private lateinit var defaultApi: DefaultApi

    @Before
    fun setup() {
        defaultApi = mockk()
        moviesService = DefaultMoviesService(defaultApi)
    }

    @Test
    fun testSearchMovies() = runBlocking {
        // Arrange
        val query = "Avengers"
        val page = 1
        val expectedResponse = InlineResponse200()

        coEvery {
            defaultApi.searchMovieGet(any(), query, page)
        } returns expectedResponse

        // Act
        val result = moviesService.searchMovies(query, page)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun testGetTrendingMovies() = runBlocking {
        // Arrange
        val expectedResponse = InlineResponse200()

        coEvery {
            defaultApi.trendingMovieDayGet(any())
        } returns expectedResponse

        // Act
        val result = moviesService.getTrendingMovies()

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun testMovieIdGet() = runBlocking {
        // Arrange
        val id = 123
        val appendToResponse = "credits"
        val expectedResponse = Movie()

        coEvery {
            defaultApi.movieIdGet(any(), id, appendToResponse)
        } returns expectedResponse

        // Act
        val result = moviesService.movieIdGet(id, appendToResponse)

        // Assert
        assertEquals(expectedResponse, result)
    }
}
