package com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class GetSearchMoviesInteractorImpl(private val moviesDBApi: MoviesDBApi) : GetSearchMoviesInteractor {
    override fun getDataMovies(callback: GetSearchMoviesInteractor.getSearchMoviesInterface, query: String) {
        val searchMovies = moviesDBApi.getSearchedMovie("c9221bf28759d13b63e8730e5af4a329", query)

        searchMovies.enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(call: Call<MovieResponse>?, movieResponse: Response<MovieResponse>?) {

                var consulta = movieResponse?.body()

                val movieListSearches = consulta!!.results

                movieListSearches?.let {
                    callback.onSuccess(movieListSearches)
                }

            }
        })
    }

}