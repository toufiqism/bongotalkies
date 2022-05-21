package com.bongo.bongotalkies.repository

import com.bongo.bongotalkies.api.RetrofitInstance
import com.bongo.bongotalkies.db.MovieResultDatabase

class MovieResultRepository(val db: MovieResultDatabase) {

    suspend fun getTopMovies(pageNumber: Int) =
        RetrofitInstance.api.getMovies(page_number = pageNumber)

    suspend fun getMovie(movieID: Int) = RetrofitInstance.api.getMovie(movieID)

}
