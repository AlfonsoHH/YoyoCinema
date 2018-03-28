package com.example.alfonsohernandez.yoyocinema.network.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
interface GoogleApi {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDwSirgtRvVHy32qzbSfHmeK8Oh21iij2Q")
    fun getNearbyPlaces(@Query("type") type: String, @Query("location") location: String, @Query("radius") radius: Int): Call<com.example.alfonsohernandez.yoyocinema.domain.models.Response>

}