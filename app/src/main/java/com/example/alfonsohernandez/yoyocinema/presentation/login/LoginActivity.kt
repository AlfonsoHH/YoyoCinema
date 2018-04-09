package com.example.alfonsohernandez.yoyocinema.presentation.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.login.LoginResult
import android.content.Intent
import com.facebook.*
import com.facebook.AccessToken
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.facebook.ProfileTracker
import com.facebook.AccessTokenTracker
import com.facebook.FacebookException
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.example.alfonsohernandez.yoyocinema.presentation.tabs.TabActivity
import com.facebook.FacebookCallback
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import com.facebook.GraphRequest
import com.urbanairship.UAirship
import dk.nodes.nstack.kotlin.NStack
import java.util.*
import dk.nodes.nstack.kotlin.inflater.NStackBaseContext
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginContract.View {


    @Inject
    lateinit var presenter: LoginPresenter

    private val TAG = "LoginActivity"

    lateinit var callbackManager: CallbackManager
    lateinit var accessTokenTracker: AccessTokenTracker
    lateinit var profileTracker: ProfileTracker

     var userMail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        UAirship.shared().getPushManager().setUserNotificationsEnabled(true)

        ///// PERMISSIONS
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        injectDependencies()
        presenter.setView(this)

        ///// TOKEN TRACKER AND PROFILE TRACKER
        accessTokenTracker = object : AccessTokenTracker() {
            override
            fun onCurrentAccessTokenChanged(oldToken: AccessToken?, newToken: AccessToken?) {
            }
        }
        accessTokenTracker.startTracking()

        ///// CALLBACK MANAGER
        callbackManager = CallbackManager.Factory.create()

        ///// CALLBACK
        val callback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {


                val request = GraphRequest.newMeRequest(loginResult.accessToken) { user, response ->
                    // Get facebook data from login
                    try {

                        presenter.firebaseEvent("2","Login success action")

                        val profile = Profile.getCurrentProfile()

                        profile?.let {
                            val userNow = UserProfile(it.getProfilePictureUri(150, 150).toString(),
                                it.firstName,
                                it.lastName,
                                user.getString("email"),
                                Locale.getDefault())

                            presenter.saveProfileData(userNow)
                        }

                        profileTracker()

                    } catch (e: JSONException) {

                    }

                }

                val parameters = Bundle()
                parameters.putString("fields", "email") // Parámetros que pedimos a facebook
                request.parameters = parameters
                request.executeAsync()

            }

            override fun onCancel() {}

            override fun onError(e: FacebookException) {
                Toast.makeText(baseContext,"Network Error",Toast.LENGTH_SHORT).show()
            }
        }

        ///// LOGIN BUTTON
        login_button.setReadPermissions(Arrays.asList("email"))
        login_button.registerCallback(callbackManager, callback)

        ///// CHECKING PREVIOUS LOGIN
        if (AccessToken.getCurrentAccessToken() != null) {
            nextActivity()
        }

        ///// FIREBASE ANALITICS
        presenter.firebaseEvent("1","Login pageview")

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        super.onStop()
        accessTokenTracker.stopTracking()
        //profileTracker.stopTracking()
    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
    }

    fun profileTracker(){
        profileTracker = object : ProfileTracker() {
        override
        fun onCurrentProfileChanged(oldProfile: Profile?, newProfile: Profile?) {

            userMail?.let {
                val userProfile = UserProfile(newProfile!!.getProfilePictureUri(150,150).toString(),
                        newProfile.firstName,newProfile.lastName,
                        it,
                        NStack.availableLanguages.get(1))
                presenter.saveProfileData(userProfile)
            } }

        }
        profileTracker.startTracking()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    override fun nextActivity() {
        val main = Intent(this, TabActivity::class.java)
        startActivity(main)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(NStackBaseContext(newBase))
    }
}
