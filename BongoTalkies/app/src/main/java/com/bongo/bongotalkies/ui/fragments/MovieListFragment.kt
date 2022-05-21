package com.bongo.bongotalkies.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bongo.bongotalkies.R
import com.bongo.bongotalkies.adapters.MovieAdapter
import com.bongo.bongotalkies.models.Movie
import com.bongo.bongotalkies.ui.MovieResultViewModel
import com.bongo.bongotalkies.ui.TopMoviesActivity
import com.bongo.bongotalkies.util.Const.Companion.QUERY_PAGE_SIZE
import com.bongo.bongotalkies.util.Resources

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    lateinit var viewModel: MovieResultViewModel
    lateinit var movieAdapter: MovieAdapter
    lateinit var rclView: RecyclerView
    lateinit var progressBar: ProgressBar


    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount

            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getTopMovies()
                isScrolling = false
            } else {
                rclView.setPadding(0, 0, 0, 0)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as TopMoviesActivity).viewModel
        rclView = view.findViewById(R.id.rclView)
        progressBar = view.findViewById(R.id.paginationProgressBar)
        setUpRecyclerView()
        movieAdapter.setOnItemClickListener {
            viewModel.movie_id = it.id
            val bundle = Bundle().apply {
                putSerializable("movie_id", it.id)
            }
            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailsFragment,
                bundle
            )

        }
        viewModel.topMovies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    response.data?.let { movieResponse ->
                        movieAdapter.differ.submitList(movieResponse.results.toList())
                        val totalAmountPages = movieResponse.total_results / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.page == totalAmountPages
                    }
                }
                is Resources.Error -> {
                    hideProgressBar()
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

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressbar() {
        progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setUpRecyclerView() {
        movieAdapter = MovieAdapter()
        rclView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@MovieListFragment.scrollListener)
        }
    }
}