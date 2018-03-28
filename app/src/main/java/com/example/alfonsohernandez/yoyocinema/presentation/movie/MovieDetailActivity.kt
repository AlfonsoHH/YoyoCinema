package com.example.alfonsohernandez.yoyocinema.presentation.movie

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    @Inject
    lateinit var presenter: MovieDetailPresenter

    lateinit var movie: MovieResultsItem

    var existe: Boolean = false

    lateinit var movieID: String

    private val TAG = "MovieDetailActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        injectDependencies()

        val list: List<MovieResultsItem> = Paper.book().read("discover")
        Log.d(TAG, list.get(0).title)

        movieID = intent.getStringExtra("movie")
        presenter.setView(this, movieID)

        presenter.loadCache(movieID)

        imageButtonFav.setOnClickListener {
            onFavoriteClicked()
        }

    }

    override fun onDestroy() {
        presenter.setView(null, movieID)
        super.onDestroy()
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    override fun setData(data: MovieResultsItem) {
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + data?.posterPath).into(photoDetail)
        titleDetail.text = data.title
        overviewDetail.text = data.overview
        dateDetail.text = data.releaseDate
        ratingDetail.rating = data.voteAverage!!.toFloat() / 2

        movie = data
    }

    fun onFavoriteClicked() {
        if (isNetworkAvailable()) {
            presenter.loadFirebaseData(movieID)
            if (!existe) {
                imageButtonFav.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.rotate))
                presenter.addFavorite(movie)
            } else {
                imageButtonFav.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.rotateback))
                presenter.eraseFavorite(movieID)
                existe=false
            }
        } else {
            Toast.makeText(applicationContext, "Action is not avaliable in without data conexion", Toast.LENGTH_LONG).show()
        }
    }

    override fun favoriteExist(value: Boolean) {
        existe = value
    }

    override fun showProgress(isLoading: Boolean) {
        println("Showing progress")
    }

    override fun showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
