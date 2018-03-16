package com.example.alfonsohernandez.yoyocinema.rest

import com.example.alfonsohernandez.yoyocinema.models.Movie
import com.example.alfonsohernandez.yoyocinema.models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
interface Api {

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String, @Query("api_key") apiKey: String): Call<Movie>

    @GET("/3/discover/movie/")
    fun getDiscoveryMovies(@Query("api_key") apiKey: String): Call<Response>

    @GET("/search/movie")
    fun getSearchedMovie(@Query("api_key") apiKey: String,@Query("query") query: String): Call<Response>

}