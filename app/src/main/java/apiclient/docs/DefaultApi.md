# DefaultApi

All URIs are relative to *https://api.themoviedb.org/3*

Method | HTTP request | Description
------------- | ------------- | -------------
[**movieIdGet**](DefaultApi.md#movieIdGet) | **GET** /movie/{id} | Get details of a movie by ID
[**searchMovieGet**](DefaultApi.md#searchMovieGet) | **GET** /search/movie | Search for movies
[**trendingMovieDayGet**](DefaultApi.md#trendingMovieDayGet) | **GET** /trending/movie/day | Get the latest trending movies


<a name="movieIdGet"></a>
# **movieIdGet**
> Movie movieIdGet(apiKey, id, appendToResponse)

Get details of a movie by ID

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*

val apiInstance = DefaultApi()
val apiKey : kotlin.String = apiKey_example // kotlin.String | 
val id : kotlin.Int = 56 // kotlin.Int | 
val appendToResponse : kotlin.String = appendToResponse_example // kotlin.String | Additional information to include in the response (e.g. videos, credits)
try {
    val result : Movie = apiInstance.movieIdGet(apiKey, id, appendToResponse)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#movieIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#movieIdGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **kotlin.String**|  |
 **id** | **kotlin.Int**|  |
 **appendToResponse** | **kotlin.String**| Additional information to include in the response (e.g. videos, credits) | [optional]

### Return type

[**Movie**](Movie.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchMovieGet"></a>
# **searchMovieGet**
> InlineResponse200 searchMovieGet(apiKey, query, page)

Search for movies

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*

val apiInstance = DefaultApi()
val apiKey : kotlin.String = apiKey_example // kotlin.String | 
val query : kotlin.String = query_example // kotlin.String | 
val page : kotlin.Int = 56 // kotlin.Int | 
try {
    val result : InlineResponse200 = apiInstance.searchMovieGet(apiKey, query, page)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#searchMovieGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#searchMovieGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **kotlin.String**|  |
 **query** | **kotlin.String**|  |
 **page** | **kotlin.Int**|  | [optional]

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="trendingMovieDayGet"></a>
# **trendingMovieDayGet**
> InlineResponse200 trendingMovieDayGet(apiKey)

Get the latest trending movies

### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*

val apiInstance = DefaultApi()
val apiKey : kotlin.String = apiKey_example // kotlin.String | 
try {
    val result : InlineResponse200 = apiInstance.trendingMovieDayGet(apiKey)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#trendingMovieDayGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#trendingMovieDayGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **kotlin.String**|  |

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

