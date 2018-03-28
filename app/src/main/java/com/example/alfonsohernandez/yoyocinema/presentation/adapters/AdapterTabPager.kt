package com.example.alfonsohernandez.yoyocinema.presentation.adapters

/**
 * Created by alfonsohernandez on 16/03/2018.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.alfonsohernandez.yoyocinema.presentation.map.MapFragment
import com.example.alfonsohernandez.yoyocinema.presentation.discover.DiscoverFragment
import com.example.alfonsohernandez.yoyocinema.presentation.favorites.FavoritesFragment
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfileFragment
import com.google.firebase.analytics.FirebaseAnalytics

class AdapterTabPager(fm: FragmentManager, private var tabCount: Int, mFirebase: FirebaseAnalytics) :
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
                return MapFragment()
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Movies pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return DiscoverFragment()
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "5")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Favorites pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return FavoritesFragment()
            }
            3 -> {
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "6")
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Profile pageview")
                mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle)
                return ProfileFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}