package com.example.alfonsohernandez.yoyocinema.presentation.profile

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import java.util.*

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
interface ProfileContract {
    interface View {

        fun setData(userProfile: UserProfile?)
        fun showError()

    }

    interface Presenter {

        fun getProfileData()
        fun saveLanguage(language: Locale)

    }
}