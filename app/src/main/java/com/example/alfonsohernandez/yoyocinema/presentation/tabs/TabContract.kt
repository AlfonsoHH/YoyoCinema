package com.example.alfonsohernandez.yoyocinema.presentation.tabs

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 03/04/2018.
 */
interface TabContract {
    interface View {

        fun setData(picture: String)
        fun showProgress(isLoading:Boolean)
        fun showError()

    }

    interface Presenter {

        fun getFavoriteIcon()
        fun firebaseEvent(id: String, activityName: String)

    }
}