package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics


import com.example.alfonsohernandez.yoyocinema.BuildConfig
import com.google.firebase.analytics.FirebaseAnalytics
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by alfonsohernandez on 06/04/2018.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class NewUseFirebaseAnalyticsInteractorImplTest {

    lateinit var testInteractor: NewUseFirebaseAnalyticsInteractorImpl

    @Before
    fun setup(){
        val context= RuntimeEnvironment.application
        testInteractor = NewUseFirebaseAnalyticsInteractorImpl(context)
    }

    @Test
    fun sendingDataFirebaseAnalytics() {
        testInteractor.sendingDataFirebaseAnalytics("0","Test")

    }

}