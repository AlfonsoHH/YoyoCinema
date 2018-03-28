package com.example.alfonsohernandez.yoyocinema.presentation.discover

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

interface DiscoverContract {
    interface View {

        fun setData(data: List<MovieResultsItem>)
        fun showProgress(isLoading:Boolean)
        fun showError()

    }

    interface Presenter {

        fun loadCache(key: String): List<MovieResultsItem>
        fun updateCache(key: String, list: List<MovieResultsItem>)

        fun loadSearchData(searchString:String)
        fun loadDiscoverData()

        fun loadSearchDataOffline(key: String)

    }
}