package com.example.alfonsohernandez.yoyocinema

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult
import com.facebook.login.LoginManager
import android.content.Intent
import android.view.View
import com.facebook.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(AccessToken.getCurrentAccessToken()!=null) {

            login_button.setOnClickListener(View.OnClickListener {

                callbackManager = CallbackManager.Factory.create()

                LoginManager.getInstance().registerCallback(callbackManager,
                        object : FacebookCallback<LoginResult> {
                            override fun onSuccess(loginResult: LoginResult) {
                                startActivity(Intent(applicationContext, TabActivity::class.java))
                            }

                            override fun onCancel() {
                                // App code
                            }

                            override fun onError(exception: FacebookException) {
                                // App code
                            }
                        })

            })
        }else{
            startActivity(Intent(applicationContext, TabActivity::class.java))
        }




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }
}
