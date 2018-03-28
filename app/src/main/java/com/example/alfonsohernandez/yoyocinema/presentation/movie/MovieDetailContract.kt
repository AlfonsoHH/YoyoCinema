package com.example.alfonsohernandez.yoyocinema.presentation.movie

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
interface MovieDetailContract {
    interface View {

        fun setData(data: MovieResultsItem)
        fun showProgress(isLoading:Boolean)
        fun showError()
        fun favoriteExist(value: Boolean)

    }

    interface Presenter {

        fun loadCache(movieId: String)

        fun addFavorite(movie: MovieResultsItem)
        fun eraseFavorite(movieId: String)

        fun loadFirebaseData(movieId: String)

    }
}