package com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import io.reactivex.Single
import retrofit2.Callback

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface GetDiscoverMoviesInteractor {
    interface getDiscoverMoviesCallback {
        fun onSuccess(result: List<MovieResultsItem>)
        fun onError(error: Throwable)
    }

    fun getDataMovies(callback: getDiscoverMoviesCallback)

    fun getDataMoviesRxJava(): Single<MovieResponse>
}