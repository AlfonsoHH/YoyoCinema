package com.example.alfonsohernandez.yoyocinema.domain.interactors.facebooklogin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.analytics.FirebaseAnalytics
import org.json.JSONException
import java.util.*

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class LoginFacebookInteractorImpl {
    /*interface FacebookLoginListener {
        fun onSuccess(userProfile: UserProfile)
        fun onError(exception: Exception)
    }

    init {
        val callbackManager = CallbackManager.Factory.create()
    }
    override fun doFacebookLogin(): FacebookCallback<LoginResult> {


        return callback
    }

    inner class Callback : FacebookCallback<LoginResult> {

        override fun onSuccess(result: LoginResult) {
            val profile = Profile.getCurrentProfile()

            val request = GraphRequest.newMeRequest(result.accessToken) { userResponse, response ->

                val user = UserProfile(profile.getProfilePictureUri(150, 150).toString(),
                        profile.firstName,
                        profile.lastName,
                        userResponse.getString("email"),
                        Locale.ENGLISH)

            }

            val parameters = Bundle()
            parameters.putString("fields", "email") // Parámetros que pedimos a facebook
            request.parameters = parameters
            request.executeAsync()
        }

        override fun onError(error: FacebookException?) {

        }

        override fun onCancel() {

        }

    }*/
}