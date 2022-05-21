package com.bongo.bongotalkies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.bongo.bongotalkies.repository.MovieResultRepository

class MovieResultProviderViewModelFactory(private val repository: MovieResultRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieResultViewModel(repository) as T
    }
}