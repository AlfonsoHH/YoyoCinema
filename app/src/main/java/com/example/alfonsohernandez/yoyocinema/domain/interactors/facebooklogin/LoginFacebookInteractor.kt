package com.example.alfonsohernandez.yoyocinema.domain.interactors.facebooklogin

import com.facebook.FacebookCallback
import com.facebook.login.LoginResult

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface LoginFacebookInteractor {
    fun doFacebookLogin(): FacebookCallback<LoginResult>
}