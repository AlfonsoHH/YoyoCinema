package com.example.alfonsohernandez.yoyocinema

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.POJOMovie.MovieFav
import com.example.alfonsohernandez.yoyocinema.POJOMovie.ResultsItem
import kotlinx.android.synthetic.main.activity_movie_detail.*
import com.google.firebase.database.*

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movie: ResultsItem
    lateinit var anim: Animation
    lateinit var animBack: Animation
    var click: Boolean = false

    lateinit var ref: DatabaseReference
    var movieID: String? = ""

    lateinit var movieFav: MovieFav
    lateinit var favoritesList: MutableList<MovieFav>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getSerializableExtra("movie") as ResultsItem

        Glide.with(this).load("http://image.tmdb.org/t/p/w185/"+movie?.posterPath).into(photoDetail)
        titleDetail.text = movie.title
        overviewDetail.text = movie.overview
        dateDetail.text = movie.releaseDate
        ratingDetail.rating = movie.voteAverage!!.toFloat()/2

        anim = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate)
        animBack = AnimationUtils.loadAnimation(applicationContext,R.anim.rotateback)

        ref = FirebaseDatabase.getInstance().getReference("MoviesFav")

        movieFav = MovieFav()
        favoritesList = mutableListOf()

        val pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)


        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    favoritesList = mutableListOf()
                    println("Entra en Data Change")
                    for (h in p0.children){
                        var movieFavFB = h.getValue(MovieFav::class.java)
                        if(movieFavFB!!.user.equals(pref?.getString("firstName","BBBBB"))) {
                            favoritesList.add(movieFavFB)
                        }
                    }

                }else{
                    favoritesList = mutableListOf()
                }
            }
        })

        imageButtonFav.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                if(isNetworkAvailable()) {
                    for (movieFavNow in favoritesList) {
                        if (movieFavNow.title.equals(movie.title)) {
                            click = true
                            movieID = movieFavNow.id
                        }
                    }

                    if (!click) {
                        imageButtonFav.startAnimation(anim)
                        click = !click

                        val movieId = ref.push().key
                        movieFav = MovieFav(movieId, movie.title, movie.overview, movie.posterPath, movie.releaseDate, movie.voteAverage, pref?.getString("firstName", "BBBBB"))
                        ref.child(movieId).setValue(movieFav).addOnCompleteListener {
                            Toast.makeText(applicationContext, "Added to favorites", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        imageButtonFav.startAnimation(animBack)
                        click = !click

                        ref.child(movieID).removeValue().addOnCompleteListener {
                            Toast.makeText(applicationContext, "Erased from favorites", Toast.LENGTH_LONG).show()
                        }

                    }
                }else{
                    Toast.makeText(applicationContext,"Action not avaliable offline",Toast.LENGTH_LONG).show()
                }
            }
        })


    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
