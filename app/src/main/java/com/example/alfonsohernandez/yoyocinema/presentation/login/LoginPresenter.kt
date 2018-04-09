package com.example.alfonsohernandez.yoyocinema.presentation.login

import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.NewUseFirebaseAnalyticsInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import java.util.*
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class LoginPresenter @Inject constructor(private val saveUserProfileInteractor: SaveUserProfileInteractor, private val NewUseFirebaseAnalyticsInteractor: NewUseFirebaseAnalyticsInteractor, private val setNStackLanguageInteractor: SetNstackLanguageInteractor): LoginContract.Presenter {

    private var view: LoginContract.View? = null

    fun setView(view: LoginContract.View?) {
        this.view = view
        setNStackLanguageInteractor.setDefaultLanguage(Locale.ENGLISH)
    }

    override fun saveProfileData(userProfile: UserProfile) {
        saveUserProfileInteractor.save(userProfile)
        view?.nextActivity()
    }

    override fun firebaseEvent(id: String, activityName: String) {
        NewUseFirebaseAnalyticsInteractor.sendingDataFirebaseAnalytics(id,activityName)
    }

}