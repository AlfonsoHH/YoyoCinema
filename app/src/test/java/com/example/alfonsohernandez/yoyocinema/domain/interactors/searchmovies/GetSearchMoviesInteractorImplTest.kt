package com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies

import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alfonsohernandez on 05/04/2018.
 */
class GetSearchMoviesInteractorImplTest {

    /*lateinit var testInteractor: GetSearchMoviesInteractorImpl

    var output: List<MovieResultsItem?>? = null

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val service = retrofit.create<MoviesDBApi>(MoviesDBApi::class.java)

        testInteractor = GetSearchMoviesInteractorImpl(service)
    }

    @Test
    fun getDataMoviesRxJava_ShouldPass() {
        testInteractor.getDataMoviesRxJava("Zoo").subscribe({
            output = it.results
        },{

        })
        assertNotEquals(output?.size,0)
    }

    @Test
    fun getDataMoviesRxJava_ShouldFail() {
        testInteractor.getDataMoviesRxJava("@ds``dfls").subscribe({
            output = it.results
        },{

        })
        assertEquals(output?.size,0)
    }*/

}