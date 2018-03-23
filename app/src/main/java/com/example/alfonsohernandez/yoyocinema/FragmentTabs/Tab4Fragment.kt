package com.example.alfonsohernandez.yoyocinema.FragmentTabs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import android.content.SharedPreferences
import android.widget.*
import com.example.alfonsohernandez.yoyocinema.LoginActivity
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.POJOOthers.Translation
import java.util.*
import dk.nodes.nstack.kotlin.NStack


@Suppress("UNREACHABLE_CODE")
/**
 * A simple [Fragment] subclass.
 */
class Tab4Fragment : Fragment() {

    lateinit var pref: SharedPreferences
    lateinit var tvNStackFN: TextView
    lateinit var tvNStackLN: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_tab4, container, false)

        var tvName = rootView.findViewById<TextView>(R.id.textViewFirstName)
        var tvLast = rootView.findViewById<TextView>(R.id.textViewLastName)
        var ivPhoto = rootView.findViewById<ImageView>(R.id.imageViewProfilePhoto)
        var tvMail = rootView.findViewById<TextView>(R.id.textViewMail)
        var spinner = rootView.findViewById<Spinner>(R.id.spinnerLanguage)

        tvNStackFN = rootView.findViewById<TextView>(R.id.tvNStackFirstName)
        tvNStackLN = rootView.findViewById<TextView>(R.id.tvNSatckLastName)

        tvNStackLN.text = Translation.defaultSection.profileFirstName
        tvNStackFN.text = Translation.defaultSection.profileLastName

        pref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)



        tvName.text = pref.getString("firstName", "BBBBB")
        tvLast.text = pref.getString("lastName", "CCCCCC")
        Glide.with(this).load(pref.getString("picture", "")).into(ivPhoto)
        tvMail.text = pref.getString("email", "DDDDD")

        //LANGUAGE

        println("Default Languages Changed ${NStack.availableLanguages.first().displayName}")

        val spinnerArrayAdapter = ArrayAdapter<Locale>(this.activity, android.R.layout.simple_spinner_item, NStack.availableLanguages)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(spinnerArrayAdapter)

        when (pref.getString("language", NStack.language.language)) {
            "es-ES" -> spinner.setSelection(0)
            "en_EN" -> spinner.setSelection(1)
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                when (spinner.selectedItem.toString()) {
                    "en_EN" -> changeLanguage("en_EN")
                    "es-ES" -> changeLanguage("es-ES")
                }
                changeLanguage(spinner.selectedItem.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }

        //LOGOUT

        val btnLogout = rootView.findViewById<Button>(R.id.buttonLogout)

        btnLogout.setOnClickListener(View.OnClickListener {
            // Logout
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                    startLoginActivity()
                }).executeAsync()
            }
        })

        return rootView

    }

    fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        activity?.finish()
    }

    fun changeLanguage(language: String) {
        if (!pref.getString("language", "en_EN").equals(language)) {

            val prefEditor = pref.edit()
            prefEditor.putString("language", language)

            prefEditor.apply()


            NStack.setLanguageByString(language)

            tvNStackLN.text = Translation.defaultSection.profileFirstName
            tvNStackFN.text = Translation.defaultSection.profileLastName

            println(NStack.language)

        }

    }


}// Required empty public constructor