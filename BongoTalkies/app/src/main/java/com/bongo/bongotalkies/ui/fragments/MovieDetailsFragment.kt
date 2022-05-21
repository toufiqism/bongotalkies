package com.bongo.bongotalkies.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bongo.bongotalkies.R
import com.bongo.bongotalkies.models.MovieDetails
import com.bongo.bongotalkies.ui.MovieResultViewModel
import com.bongo.bongotalkies.ui.TopMoviesActivity
import com.bongo.bongotalkies.util.Const
import com.bongo.bongotalkies.util.Resources
import com.bumptech.glide.Glide

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    lateinit var viewModel: MovieResultViewModel
    lateinit var progressBar: ProgressBar
    lateinit var imageViewPoster: ImageView
    lateinit var textViewDescription: TextView
    lateinit var textViewTitle: TextView
    lateinit var textViewDts: TextView
    lateinit var textViewDts2: TextView
    lateinit var textViewDts3: TextView
    val args: MovieDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        val movie_id = args.movieId
        viewModel.movie_id = movie_id
        viewModel.getMovie()

        viewModel.movie.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    loadData(response)
                }
                is Resources.Error -> {
                    hideProgressBar()
                    textViewDescription.text = response.message
                    response.message?.let { message ->
                        Log.e("MyTag", message)
                    }
                }
                is Resources.Loading -> {
                    showProgressbar()
                }
                else -> {}
            }

        })
    }

    private fun loadData(response: Resources.Success<MovieDetails>) {
        imageViewPoster.visibility = View.VISIBLE
        textViewTitle.visibility = View.VISIBLE
        Glide.with(this).load(Const.image_cdn + response.data?.backdropPath)
            .into(imageViewPoster)
        textViewTitle.text = response.data?.title
        textViewDescription.text = response.data?.overview
        textViewDts.text = "Release: " + response.data?.releaseDate
        textViewDts2.text = "Budget: " + response.data?.budget + "$"
        textViewDts3.text = "Revenue: " + response.data?.revenue + "$"
    }

    private fun initViews(view: View) {
        viewModel = (activity as TopMoviesActivity).viewModel
        progressBar = view.findViewById(R.id.detailsProgressBar)
        imageViewPoster = view.findViewById(R.id.imageViewPoster)
        textViewDts = view.findViewById(R.id.textViewDts)
        textViewDts2 = view.findViewById(R.id.textViewDts2)
        textViewDts3 = view.findViewById(R.id.textViewDts3)
        textViewDescription = view.findViewById(R.id.textViewDescription)
        textViewTitle = view.findViewById(R.id.textViewTitle)
    }

    private fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE

    }
}