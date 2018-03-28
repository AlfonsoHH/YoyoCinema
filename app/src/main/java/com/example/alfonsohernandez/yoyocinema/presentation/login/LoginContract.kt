package com.example.alfonsohernandez.yoyocinema.presentation.login

import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface LoginContract {
    interface View {
        fun nextActivity()
    }

    interface Presenter {
        fun saveProfileData(userProfile: UserProfile)
        fun firebaseEvent(id:String,activityName:String)
    }
}