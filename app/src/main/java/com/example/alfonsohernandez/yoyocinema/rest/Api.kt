package com.example.alfonsohernandez.yoyocinema.rest

import com.example.alfonsohernandez.yoyocinema.models.Movie
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
interface Api {
    @GET("movie") fun getMovie(): Call<Movie>
}