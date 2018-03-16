package com.example.alfonsohernandez.yoyocinema

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.models.ResultsItem
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: ResultsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getSerializableExtra("movie") as ResultsItem

        Glide.with(this).load("http://image.tmdb.org/t/p/w185/"+movie?.posterPath).into(photoDetail)
        titleDetail.text = movie.title
        overviewDetail.text = movie.overview
        languageDetail.text = movie.originalLanguage
        dateDetail.text = movie.releaseDate
        println(movie.popularity)
        ratingDetail.rating = movie.voteAverage!!.toFloat()/2

    }
}
