package com.example.alfonsohernandez.yoyocinema.presentation.login

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
import android.util.Log
import android.widget.ArrayAdapter
import com.facebook.ProfileTracker
import com.facebook.AccessTokenTracker
import com.facebook.FacebookException
import android.widget.Toast
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.Translation
import com.example.alfonsohernandez.yoyocinema.domain.models.User
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.example.alfonsohernandez.yoyocinema.presentation.TabActivity
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfilePresenter
import com.facebook.FacebookCallback
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.android.gms.analytics.GoogleAnalytics
import java.util.*
import com.google.firebase.analytics.FirebaseAnalytics
import dk.nodes.nstack.kotlin.NStack
import dk.nodes.nstack.kotlin.inflater.NStackBaseContext
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginContract.View {


    @Inject
    lateinit var presenter: LoginPresenter

    private val TAG = "LoginActivity"

    lateinit var callbackManager: CallbackManager
    lateinit var accessTokenTracker: AccessTokenTracker
    lateinit var profileTracker: ProfileTracker

    var userEmail = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }

        injectDependencies()
        presenter.setView(this)

        accessTokenTracker = object : AccessTokenTracker() {
            override
            protected fun onCurrentAccessTokenChanged(oldToken: AccessToken?, newToken: AccessToken?) {
            }
        }

        profileTracker = object : ProfileTracker() {
            override
            protected fun onCurrentProfileChanged(oldProfile: Profile?, newProfile: Profile?) {
                presenter.saveProfileData(newProfile)
            }
        }

        accessTokenTracker.startTracking()
        profileTracker.startTracking()

        callbackManager = CallbackManager.Factory.create()

        val loginButton = findViewById<View>(R.id.login_button) as LoginButton

        val callback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val profile = Profile.getCurrentProfile()

                val request = GraphRequest.newMeRequest(loginResult.accessToken) { user, response ->
                    // Get facebook data from login
                    try {

                        presenter.firebaseEvent("2","Login success action")

                        val userNow = UserProfile(profile.getProfilePictureUri(150, 150).toString(),
                                profile.firstName,
                                profile.lastName,
                                user.getString("email"),
                                Locale.ENGLISH)

                        presenter.saveProfileData(userNow)

                        Log.d(TAG, "This is the mail: " + user.getString("email"))

                        nextActivity(profile)

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
        loginButton.registerCallback(callbackManager, callback)

        if (AccessToken.getCurrentAccessToken() != null) {
            nextActivity()
        }

        presenter.firebaseEvent("1","Login pageview")

    }

    override fun onResume() {
        super.onResume()
        val profile = Profile.getCurrentProfile()
        nextActivity(profile)
    }

    override fun onStop() {
        super.onStop()
        accessTokenTracker.stopTracking()
        profileTracker.stopTracking()
    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
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
