package com.example.alfonsohernandez.yoyocinema.presentation.tabs

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.NewUseFirebaseAnalyticsInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 03/04/2018.
 */
class TabPresenter @Inject constructor(private val getUserProfileInteractor: GetUserProfileInteractor, private val NewUseFirebaseAnalyticsInteractor: NewUseFirebaseAnalyticsInteractor) : TabContract.Presenter {

    private var view: TabContract.View? = null
    private val TAG: String = "TabPresenter"

    fun setView(view: TabContract.View?) {
        this.view = view
        getFavoriteIcon()
    }

    override fun getFavoriteIcon() {
        Log.d(TAG,getUserProfileInteractor.getProfile()?.language.toString())
        view?.setData(getUserProfileInteractor.getProfile()?.picture!!)
    }

    override fun firebaseEvent(id: String, activityName: String) {
        NewUseFirebaseAnalyticsInteractor.sendingDataFirebaseAnalytics(id,activityName)
    }
}