package com.example.flixster

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class UpcomingMovieDetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var abstractTextView: TextView
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //Find the views for the screen
        mediaImageView = findViewById(R.id.media_image)
        titleTextView = findViewById(R.id.media_title)
        languageTextView = findViewById(R.id.language)
        abstractTextView = findViewById(R.id.overview)
        dateTextView = findViewById(R.id.release_date)



        //Set the title, language, release date and overview information from the movie
        titleTextView.text = intent.getStringExtra("title")
        languageTextView.text = "LANGUAGE: us-" + intent.getStringExtra("language")
        abstractTextView.text = intent.getStringExtra("abstract")
        dateTextView.text = "RELEASE DATE: " + intent.getStringExtra("date")
        val imgUrl = intent.getStringExtra("image")
        //Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + imgUrl)
            .into(mediaImageView)
    }
}