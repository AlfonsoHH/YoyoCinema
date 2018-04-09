package com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces

import com.example.alfonsohernandez.yoyocinema.domain.models.ResultsItem
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApi
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GetGoogleMapNearbyPlacesInteractorImplTest {

    /*lateinit var testInteractor: GetGoogleMapNearbyPlacesInteractorImpl

    var output: List<ResultsItem?>? = null
    val search = "movio_theater"
    val searchGood = "movie_theater"
    val position = "37.4219983,-122.084"
    val radius = 10000*/

    @Before
    fun setUp() {
        /*val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val service = retrofit.create<GoogleApi>(GoogleApi::class.java)

        testInteractor = GetGoogleMapNearbyPlacesInteractorImpl(service)*/
    }

    @Test
    fun getNearbyPlaces_ShouldFail() {


        /*testInteractor.getNearbyPlaces(search,position,radius)
                .subscribe(
                        {
                            output = it.results
                        },
                        {

                        }
                )

        assertEquals(output?.size, 0)*/
    }

    @Test
    fun getNearbyPlaces_ShouldPass() {

        /*testInteractor.getNearbyPlaces(searchGood, position, radius)
                .subscribe(
                        {
                            output = it.results
                        },
                        {

                        }
                )

        assertNotEquals(output?.size, 0)*/
    }

}