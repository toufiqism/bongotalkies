package com.bongo.bongotalkies.api

import com.bongo.bongotalkies.models.Movie
import com.bongo.bongotalkies.util.Const.Companion.api_key
import com.bongo.bongotalkies.models.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/3/movie/top_rated")
    suspend fun getMovies(
        @Query("api_key")
        apiKey: String = api_key,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page_number: Int
    ): Response<Movie>

    @GET("/3/movie/{id}")
    suspend fun getMovie(
        @Path("id")
        movie_id: Int,
        @Query("api_key")
        apiKey: String = api_key,
        @Query("language")
        language: String = "en-US"
    ): Response<MovieDetails>
}



//https://api.themoviedb.org/top_rated?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US&page=1

//https://api.themoviedb.org/3/movie/top_rated?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US&page=1

//https://api.themoviedb.org/3/movie/<movie_id>?api_key=<api_key>&language=en-US

//https://api.themoviedb.org/3/movie?movie_id=278&api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US

//https://api.themoviedb.org/3?movie=278&api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US

//https://api.themoviedb.org/3/movie/122?api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US

//https://api.themoviedb.org/3/movie?=278&api_key=c37d3b40004717511adb2c1fbb15eda4&language=en-US