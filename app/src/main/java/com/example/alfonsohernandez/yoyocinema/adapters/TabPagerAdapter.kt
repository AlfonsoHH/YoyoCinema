package com.example.alfonsohernandez.yoyocinema.adapters

/**
 * Created by alfonsohernandez on 16/03/2018.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.alfonsohernandez.yoyocinema.FragmentTabs.Tab1Fragment
import com.example.alfonsohernandez.yoyocinema.FragmentTabs.Tab2Fragment
import com.example.alfonsohernandez.yoyocinema.FragmentTabs.Tab3Fragment
import com.example.alfonsohernandez.yoyocinema.FragmentTabs.Tab4Fragment
import com.google.firebase.analytics.FirebaseAnalytics

class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int,mFirebase: FirebaseAnalytics) :
        FragmentPagerAdapter(fm) {

    var  mFirebaseAnalytics: FirebaseAnalytics? = null

    init {
        this.mFirebaseAnalytics = mFirebase
    }

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Map pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return Tab1Fragment()
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Movies pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return Tab2Fragment()
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "5")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Favorites pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return Tab3Fragment()
            }
            3 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "6")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Profile pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return Tab4Fragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}