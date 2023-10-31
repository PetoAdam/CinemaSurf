# io.swagger.client - Kotlin client library for CinemaSurf API (using TMDB)

## Requires

* Kotlin 1.1.2
* Gradle 3.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in Swagger definitions.
* Implementation of ApiClient is intended to reduce method counts, specifically to benefit Android targets.

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://api.themoviedb.org/3*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**movieIdGet**](docs/DefaultApi.md#movieidget) | **GET** /movie/{id} | Get details of a movie by ID
*DefaultApi* | [**searchMovieGet**](docs/DefaultApi.md#searchmovieget) | **GET** /search/movie | Search for movies
*DefaultApi* | [**trendingMovieDayGet**](docs/DefaultApi.md#trendingmoviedayget) | **GET** /trending/movie/day | Get the latest trending movies


<a name="documentation-for-models"></a>
## Documentation for Models

 - [io.swagger.client.models.InlineResponse200](docs/InlineResponse200.md)
 - [io.swagger.client.models.Movie](docs/Movie.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

All endpoints do not require authorization.
