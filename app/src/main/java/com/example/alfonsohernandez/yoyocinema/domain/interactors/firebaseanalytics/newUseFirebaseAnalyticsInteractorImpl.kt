package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class newUseFirebaseAnalyticsInteractorImpl: newUseFirebaseAnalyticsInteractor {
    var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun sendingDataFirebaseAnalytics(id: String, activityName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, activityName)
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}