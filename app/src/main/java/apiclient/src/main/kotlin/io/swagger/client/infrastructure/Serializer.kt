package io.swagger.client.infrastructure

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import io.swagger.client.models.MovieDateAdapter
import java.util.*

object Serializer {
    @JvmStatic
    val moshi: Moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            //.add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(MovieDateAdapter())
            .build()
}
