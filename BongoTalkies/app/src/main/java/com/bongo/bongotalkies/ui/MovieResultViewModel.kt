package com.bongo.bongotalkies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongo.bongotalkies.models.Movie
import com.bongo.bongotalkies.models.MovieDetails
import com.bongo.bongotalkies.repository.MovieResultRepository
import com.bongo.bongotalkies.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieResultViewModel(val repository: MovieResultRepository) : ViewModel() {

    val topMovies: MutableLiveData<Resources<Movie>> = MutableLiveData()
    val movie: MutableLiveData<Resources<MovieDetails>> = MutableLiveData()
    var page = 1
    var movieResponse: Movie? = null

    var movie_id: Int? = null

    init {
        getTopMovies()
    }

    fun getTopMovies() = viewModelScope.launch {
        topMovies.postValue(Resources.Loading())
        val response = repository.getTopMovies(page)
        topMovies.postValue(handleTopMoviesResponse(response))
    }

    fun getMovie() = viewModelScope.launch {
        movie.postValue(Resources.Loading())
        val response = repository.getMovie(movie_id!!)
        movie.postValue(handleMovieResponse(response))
    }

    private fun handleMovieResponse(response: Response<MovieDetails>): Resources<MovieDetails> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(response.message())
    }

    private fun handleTopMoviesResponse(response: Response<Movie>): Resources<Movie> {
        if (response.isSuccessful) {
            response.body()?.let {
                page++
                if (movieResponse == null) {
                    movieResponse = it
                } else {
                    val old = movieResponse?.results
                    val new = it.results
                    old?.addAll(new)
                }
                return Resources.Success(movieResponse ?: it)
            }
        }
        return Resources.Error(response.message())
    }
}