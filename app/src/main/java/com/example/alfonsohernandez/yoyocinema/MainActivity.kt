package com.example.alfonsohernandez.yoyocinema

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.alfonsohernandez.yoyocinema.adapters.MyAdapter
import com.example.alfonsohernandez.yoyocinema.models.Movie
import com.example.alfonsohernandez.yoyocinema.models.Translation
import com.example.alfonsohernandez.yoyocinema.models.User
import com.example.alfonsohernandez.yoyocinema.rest.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var apiService = ApiService()

    lateinit var movieList: MutableList<Movie>
    lateinit var actualUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //textView.text = Translation.defaultSection.ok

        val myToolbar = findViewById<Toolbar>(R.id.toolbarMovies)
        setSupportActionBar(toolbarMovies)
        supportActionBar!!.setTitle("Movies")

        val activity: Activity = this

        //actualUser = intent.getSerializableExtra("user") as User
        actualUser = User("Alfon","Weeee","mail@mail.com","url")

        /*var movieCall = apiService.createService().getMovie()
        apiService.createService().getMovie().enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                textView.text = response?.body()?.original_title
            }
        })*/

        val rv = findViewById<RecyclerView>(R.id.rvMovies)

        movieList = mutableListOf()
        movieList.add(0,Movie("The Godfather","The Godfather","Mafia movie"))
        movieList.add(1,Movie("The Godfather","The Godfather","Mafia movie"))
        movieList.add(2,Movie("The Godfather","The Godfather","Mafia movie"))



        var adapter = MyAdapter(movieList,activity,actualUser)
        rv.adapter = adapter



    }
}
