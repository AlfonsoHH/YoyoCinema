package com.example.alfonsohernandez.yoyocinema

import android.app.Application
import com.example.alfonsohernandez.yoyocinema.POJOOthers.Translation
import dk.nodes.nstack.kotlin.NStack


/**
 * Created by alfonsohernandez on 20/03/2018.
 */
class App : Application(){

    //private var sAnalytics: GoogleAnalytics? = null
    //private var sTracker: Tracker? = null

    override fun onCreate() {
        super.onCreate()

        NStack.debugMode = true
        NStack.translationClass = Translation::class.java
        NStack.init(this)

        //sAnalytics = GoogleAnalytics.getInstance(this);
    }

    /*@Synchronized
    fun getDefaultTracker(): Tracker {
        if (sTracker == null) {
            sTracker = sAnalytics!!.newTracker(R.xml.global_tracker)
        }

        return sTracker!!
    }*/

}