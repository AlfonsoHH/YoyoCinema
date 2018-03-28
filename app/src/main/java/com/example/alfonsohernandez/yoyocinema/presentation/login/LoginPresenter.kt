package com.example.alfonsohernandez.yoyocinema.presentation.login

import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.newUseFirebaseAnalyticsInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class LoginPresenter @Inject constructor(private val saveUserProfileInteractor: SaveUserProfileInteractor, private val getUserProfileInteractor: GetUserProfileInteractor, private val newUseFirebaseAnalyticsInteractor: newUseFirebaseAnalyticsInteractor): LoginContract.Presenter {

    private var view: LoginContract.View? = null

    fun setView(view: LoginContract.View?) {
        this.view = view
    }

    override fun saveProfileData(userProfile: UserProfile) {
        saveUserProfileInteractor.save(userProfile)
        view?.nextActivity()
    }

    override fun firebaseEvent(id: String, activityName: String) {
        newUseFirebaseAnalyticsInteractor.sendingDataFirebaseAnalytics(id,activityName)
    }

}