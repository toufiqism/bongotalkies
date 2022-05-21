package com.bongo.bongotalkies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bongo.bongotalkies.R
import com.bongo.bongotalkies.db.MovieResultDatabase
import com.bongo.bongotalkies.repository.MovieResultRepository

class TopMoviesActivity : AppCompatActivity() {
    lateinit var viewModel: MovieResultViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_movies)

        val movieRepo = MovieResultRepository(MovieResultDatabase(this))
        val viewModelProviderFactory = MovieResultProviderViewModelFactory(movieRepo)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MovieResultViewModel::class.java)
    }
}