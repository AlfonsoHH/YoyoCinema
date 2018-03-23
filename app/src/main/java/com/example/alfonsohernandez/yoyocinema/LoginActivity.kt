package com.example.alfonsohernandez.yoyocinema

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.login.LoginResult
import android.content.Intent
import android.view.View
import com.facebook.*
import com.facebook.AccessToken
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.facebook.ProfileTracker
import com.facebook.AccessTokenTracker
import com.facebook.FacebookException
import android.widget.Toast
import com.example.alfonsohernandez.yoyocinema.POJOOthers.Translation
import com.facebook.FacebookCallback
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import com.facebook.GraphRequest
import com.google.android.gms.analytics.GoogleAnalytics
import java.util.*
import com.google.firebase.analytics.FirebaseAnalytics
import dk.nodes.nstack.kotlin.NStack


class LoginActivity : AppCompatActivity() {


    lateinit var callbackManager: CallbackManager

    lateinit var accessTokenTracker: AccessTokenTracker
    lateinit var profileTracker: ProfileTracker

    var Semail = ""

    var  mFirebaseAnalytics: FirebaseAnalytics? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val pref = getSharedPreferences("pref",Context.MODE_PRIVATE)

        Semail = pref.getString("email","")

        when (pref.getString("language", "")) {
            "es-ES" -> {
                NStack.setLanguageByString("es-ES")
            }
            "en_EN" -> {
                NStack.setLanguageByString("en_EN")
            }
        }

        tvPlease.text = Translation.defaultSection.loginPlease
        tvThanks.text = Translation.defaultSection.loginThanks

        callbackManager = CallbackManager.Factory.create()

        accessTokenTracker = object : AccessTokenTracker() {
            override
            protected fun onCurrentAccessTokenChanged(oldToken: AccessToken?, newToken: AccessToken?) {}
        }

        profileTracker = object : ProfileTracker() {
            override
            protected fun onCurrentProfileChanged(oldProfile: Profile?, newProfile: Profile?) {
                nextActivity(newProfile)
            }
        }
        accessTokenTracker.startTracking()
        profileTracker.startTracking()

        val loginButton = findViewById<View>(R.id.login_button) as LoginButton

        val callback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val profile = Profile.getCurrentProfile()

                val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                    // Get facebook data from login
                    try {

                        Semail = `object`.getString("email")

                        val bundle = Bundle()
                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2")
                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Login success action")
                        bundle.putString(FirebaseAnalytics.Param.SIGN_UP_METHOD, "facebook")
                        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.LOGIN,bundle)

                        nextActivity(profile)
                        Toast.makeText(applicationContext, "Logging in...", Toast.LENGTH_SHORT).show()

                    } catch (e: JSONException) {

                    }

                }

                val parameters = Bundle()
                parameters.putString("fields", "email") // Parámetros que pedimos a facebook
                request.parameters = parameters
                request.executeAsync()


            }

            override fun onCancel() {}

            override fun onError(e: FacebookException) {}
        }
        loginButton.setReadPermissions(Arrays.asList("email"))
        //loginButton.setReadPermissions("public_profile email")
        loginButton.registerCallback(callbackManager, callback)

        if(AccessToken.getCurrentAccessToken() != null) {
            val profile = Profile.getCurrentProfile()
            nextActivity(profile)
        }

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Login pageview")
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)

    }

    override fun onResume() {
        super.onResume()
        //Facebook login
        val profile = Profile.getCurrentProfile()
        nextActivity(profile)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        //Facebook login
        accessTokenTracker.stopTracking()
        profileTracker.stopTracking()

        System.out.println("Feature")

        GoogleAnalytics.getInstance(this@LoginActivity).reportActivityStop(this)
    }

    override fun onStart() {
        super.onStart()
        GoogleAnalytics.getInstance(this@LoginActivity).reportActivityStart(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

    }

    private fun nextActivity(profile: Profile?) {
        if (profile != null) {
            val main = Intent(this, TabActivity::class.java)

            val pref = getSharedPreferences("pref",Context.MODE_PRIVATE)
            val prefEditor = pref.edit()
            prefEditor.putString("firstName",profile.firstName)
            prefEditor.putString("lastName",profile.lastName)
            prefEditor.putString("email",Semail)
            prefEditor.putString("picture",profile.getProfilePictureUri(150,150).toString())

            prefEditor.apply()

            startActivity(main)
        }
    }
}
