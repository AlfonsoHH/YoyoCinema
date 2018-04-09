package com.example.alfonsohernandez.yoyocinema.network.rest

import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import io.reactivex.Single
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
interface GoogleApi {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDwSirgtRvVHy32qzbSfHmeK8Oh21iij2Q")
    fun getNearbyPlaces(@Query("type") type: String, @Query("location") location: String, @Query("radius") radius: Int): Single<Response>

}