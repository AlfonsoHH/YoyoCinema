package com.example.alfonsohernandez.yoyocinema.presentation.profile

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import java.util.*
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class ProfilePresenter @Inject constructor(
        private val getUserProfileInteractor: GetUserProfileInteractor,
        private val saveUserProfileInteractor: SaveUserProfileInteractor,
        private val setNStackLanguageInteractor: SetNstackLanguageInteractor
) : ProfileContract.Presenter {


    private val TAG = "ProfilePresenter"

    private var view: ProfileContract.View? = null

    fun setView(view: ProfileContract.View?) {
        this.view = view
        getProfileData()
    }

    override fun getProfileData() {
        val userProfile = getUserProfileInteractor.getProfile()
        //Log.d(TAG,"onSetView: "+userProfile?.language?.language)

        view?.setData(userProfile)
    }

    override fun saveLanguage(language: Locale) {
        setNStackLanguageInteractor.setDefaultLanguage(language)
        var profile = getUserProfileInteractor.getProfile()
        profile?.language = language
        saveUserProfileInteractor.save(profile)
    }
}