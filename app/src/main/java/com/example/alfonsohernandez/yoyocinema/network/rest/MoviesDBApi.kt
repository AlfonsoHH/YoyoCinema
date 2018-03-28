package com.example.alfonsohernandez.yoyocinema.network.rest

import com.example.alfonsohernandez.yoyocinema.domain.models.Movie
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
interface MoviesDBApi {

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String, @Query("api_key") apiKey: String): Call<Movie>

    @GET("/3/discover/movie/")
    fun getDiscoveryMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("/3/search/movie")
    fun getSearchedMovie(@Query("api_key") apiKey: String,@Query("query") query: String): Call<MovieResponse>

}