/**
* CinemaSurf API (using TMDB)
* No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
*
* OpenAPI spec version: 1.0.0
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package io.swagger.client.models

import com.example.cinemasurf.db.FavouriteMovie
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


/**
 * 
 * @param id 
 * @param title 
 * @param homepage 
 * @param overview 
 * @param poster_path 
 * @param release_date 
 * @param revenue 
 * @param runtime 
 * @param vote_average 
 * @param vote_count 
 */
data class Movie (
    val id: kotlin.Int? = null,
    val title: kotlin.String? = null,
    val homepage: kotlin.String? = null,
    val overview: kotlin.String? = null,
    val poster_path: kotlin.String? = null,
    val release_date: java.util.Date? = null,
    val revenue: kotlin.Long? = null,
    val runtime: kotlin.Int? = null,
    val vote_average: Float? = null,
    val vote_count: kotlin.Int? = null
) {
    constructor(movie: FavouriteMovie) : this(
        movie.id ?: 0,
        movie.title,
        movie.homepage,
        movie.overview,
        movie.poster_path,
        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(movie.release_date),
        movie.revenue,
        movie.runtime,
        movie.vote_average,
        movie.vote_count
    )
}

class MovieDateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @FromJson
    fun fromJson(jsonDate: String?): Date? {
        return if (jsonDate != null && jsonDate.isNotEmpty()) {
            try {
                dateFormat.parse(jsonDate)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    @ToJson
    fun toJson(date: Date?): String? {
        return if (date != null) {
            dateFormat.format(date)
        } else {
            null
        }
    }
}


