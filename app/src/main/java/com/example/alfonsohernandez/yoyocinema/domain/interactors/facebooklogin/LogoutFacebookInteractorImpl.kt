package com.example.alfonsohernandez.yoyocinema.domain.interactors.facebooklogin

import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class LogoutFacebookInteractorImpl: LogoutFacebookInteractor {
    override fun logoutFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                AccessToken.setCurrentAccessToken(null)
                LoginManager.getInstance().logOut()
                //startLoginActivity()
            }).executeAsync()
        }
    }

}