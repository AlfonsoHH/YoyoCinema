package com.example.alfonsohernandez.yoyocinema.presentation.map

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.models.ResultsItem

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
interface MapContract {
    interface View {

        fun setData(data: List<ResultsItem?>?)
        fun showProgress(isLoading:Boolean)
        fun showError()

    }

    interface Presenter {

        //fun loadCache(key: String): List<ResultsItem>
        //fun updateCache(key: String, list: List<ResultsItem>)
        fun loadSearchData(searchString:String,position:String,radius:Int)

    }
}