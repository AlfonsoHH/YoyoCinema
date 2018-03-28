package com.example.alfonsohernandez.yoyocinema.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import android.widget.*
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.presentation.login.LoginActivity
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import java.util.*
import dk.nodes.nstack.kotlin.NStack
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


@Suppress("UNREACHABLE_CODE")
/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment(), ProfileContract.View {

    private val TAG = "ProfileFragment"
    private val SPANISH = Locale("es", "ES")

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        var spinnerArrayAdapter = ArrayAdapter<Locale>(context, android.R.layout.simple_spinner_item, NStack.availableLanguages)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerLanguage.setAdapter(spinnerArrayAdapter)

        presenter.setView(this)

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedLocale = spinnerLanguage.selectedItem as Locale
                Log.d(TAG, "NStack LOCALE SELECTED $selectedLocale")
                presenter.saveLanguage(selectedLocale)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        //FACEBOOK BUTTON
        buttonLogout.setOnClickListener(View.OnClickListener {
            // Logout
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                    startLoginActivity()
                }).executeAsync()
            }
        })

    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    override fun setData(userProfile: UserProfile?) {
        Glide.with(this).load(userProfile?.picture).into(imageViewProfilePhoto)
        textViewFirstName.text = userProfile?.firstName
        textViewLastName.text = userProfile?.lastName
        textViewMail.text = userProfile?.email

        when (userProfile?.language) {
            NStack.availableLanguages[0] -> {
                spinnerLanguage.setSelection(0)
                Log.d(TAG,"Inside Opcion 1")
            }
            NStack.availableLanguages[1] -> {
                spinnerLanguage.setSelection(1)
                Log.d(TAG,"Inside Opcion 2")
            }
        }
    }

    override fun setTranslation(firstName: String, lastName: String) {
        tvNStackFirstName.text = firstName
        tvNSatckLastName.text = lastName
    }

    override fun showError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }


}// Required empty public constructor