package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UpcomingMovieRecyclerViewAdapter(
    private val movies: List<UpcomingMovieObj>,
    private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<UpcomingMovieRecyclerViewAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: UpcomingMovieObj? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        val mMovieImage: ImageView = mView.findViewById(R.id.movie_image) as ImageView
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.mItem = movie
        holder.mMovieTitle.text = movie.title

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterImageUrl)
            //.placeholder(R.drawable.flix_1)
            //.error(R.drawable.x_1)
            //.transition(DrawableTransitionOptions.withCrossFade(2000))
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

