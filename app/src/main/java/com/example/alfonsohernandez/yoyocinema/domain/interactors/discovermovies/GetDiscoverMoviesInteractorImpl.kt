package com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class GetDiscoverMoviesInteractorImpl(private val moviesDBApi: MoviesDBApi) : GetDiscoverMoviesInteractor {

    override fun getDataMoviesRxJava(): Single<MovieResponse> {
        return moviesDBApi.getDiscoveryMoviesRxJava("c9221bf28759d13b63e8730e5af4a329")
    }

    override fun getDataMovies(callback: GetDiscoverMoviesInteractor.getDiscoverMoviesCallback) {

        var discoverMovies = moviesDBApi.getDiscoveryMovies("c9221bf28759d13b63e8730e5af4a329")

        discoverMovies.enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable) {
                callback.onError(t)
            }

            override fun onResponse(call: Call<MovieResponse>?, movieResponse: Response<MovieResponse>?) {

                var consulta = movieResponse?.body()

                val movieList = consulta!!.results

                movieList?.let {
                    callback.onSuccess(movieList)
                }
            }

        })
    }
}