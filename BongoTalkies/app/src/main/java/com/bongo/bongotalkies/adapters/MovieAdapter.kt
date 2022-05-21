package com.bongo.bongotalkies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bongo.bongotalkies.R
import com.bongo.bongotalkies.models.Movie
import com.bongo.bongotalkies.models.Result
import com.bongo.bongotalkies.util.Const.Companion.image_cdn
import com.bumptech.glide.Glide

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_2, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.itemView.apply {

            Glide.with(this).load(image_cdn + movie.poster_path)
                .into(findViewById<ImageView>(R.id.ivArticleImage))
//            findViewById<TextView>(R.id.tvSource).text = getBigTitle(movie.original_title)
            findViewById<TextView>(R.id.tvTitle).text = movie.title
            findViewById<TextView>(R.id.tvDescription).text = movie.overview
            findViewById<TextView>(R.id.tvPublishedAt).text = "Release Date: " + movie.release_date
            setOnClickListener {
                onItemClickListener?.let {
                    it(movie)
                }
            }

        }
    }

    fun getBigTitle(title: String): String {
        if (title.length > 15) {
            return title.substring(0, 15) + "..."
        } else {
            return title
        }
    }


    private var onItemClickListener: ((Result) -> Unit)? = null
    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }
}
