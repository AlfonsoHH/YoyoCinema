package com.example.alfonsohernandez.yoyocinema.storage.preferences

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class MyPreferencesManager(context: Context) {

    lateinit var pref: SharedPreferences

    init {
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    fun getPreferencesParameter(parameter: String):String{
        return pref.getString(parameter,"")
    }
    fun savePreferencesParameter(parameter: String,what: String){
        val prefEditor = pref.edit()
        prefEditor.putString(parameter,what)
        prefEditor.apply()
    }
}