package com.example.alfonsohernandez.yoyocinema

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import com.example.alfonsohernandez.yoyocinema.models.Movie
import com.example.alfonsohernandez.yoyocinema.models.User
import com.example.alfonsohernandez.yoyocinema.rest.ApiService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var apiService = ApiService()

    var movieList: MutableList<Movie>? = mutableListOf()
    lateinit var actualUser: User
    lateinit var actualMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textView.text = Translation.defaultSection.ok

        val myToolbar = findViewById<Toolbar>(R.id.toolbarMovies)
        setSupportActionBar(toolbarMovies)
        supportActionBar!!.setTitle("Movies")

        val rv = findViewById<RecyclerView>(R.id.rvMovies)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val activity: Activity = this

        //actualUser = intent.getSerializableExtra("user") as User
        actualUser = User("Alfon", "Weeee", "mail@mail.com", "url")

        /*var movieCall = apiService.createService().getMovie("550", "c9221bf28759d13b63e8730e5af4a329")
        movieCall.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                println(response?.body())
                movieList!!.add(0,response?.body())

                println(movieList!!.get(0)?.title)
                var adapter = AdapterListDiscoveries(movieList, activity, actualUser)
                rv.adapter = adapter
            }
        })
*/



    }
}
