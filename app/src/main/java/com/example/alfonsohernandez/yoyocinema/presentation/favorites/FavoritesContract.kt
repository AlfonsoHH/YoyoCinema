package com.example.alfonsohernandez.yoyocinema.presentation.favorites

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface FavoritesContract {
    interface View {

        fun setData(data: List<MovieResultsItem>)
        fun showProgress(isLoading:Boolean)
        fun showError()
        fun showNoResults()

    }

    interface Presenter {

        fun loadCache(): List<MovieResultsItem>
        fun updateUserCache(list: List<MovieResultsItem>)
        fun updateCache(key: String, list: List<MovieResultsItem>)

        fun loadSearchData(searchString: String)
        fun loadFirebaseData()

        fun loadSearchDataOffline(searchString: String)
    }
}