package com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.reactivex.Single


/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface GetSearchMoviesInteractor {
    interface getSearchMoviesInterface {
        fun onSuccess(result: List<MovieResultsItem>)
        fun onError(error: Throwable)
    }

    fun getDataMovies(callback: getSearchMoviesInterface, query: String)

    fun getDataMoviesRxJava(query:String): Single<MovieResponse>
}