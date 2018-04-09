package com.example.alfonsohernandez.yoyocinema.presentation.adapters

/**
 * Created by alfonsohernandez on 16/03/2018.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.alfonsohernandez.yoyocinema.presentation.map.MapFragment
import com.example.alfonsohernandez.yoyocinema.presentation.discover.DiscoverFragment
import com.example.alfonsohernandez.yoyocinema.presentation.favorites.FavoritesFragment
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfileFragment
import com.example.alfonsohernandez.yoyocinema.presentation.tabs.TabPresenter

class AdapterTabPager(fm: FragmentManager, private var tabCount: Int, presenter: TabPresenter) :
        FragmentPagerAdapter(fm) {

    var  presenter: TabPresenter? = null

    init {
        this.presenter = presenter
    }

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> {
                presenter!!.firebaseEvent("3","Map pageview")
                return MapFragment()
            }
            1 -> {
                presenter!!.firebaseEvent("4","Movies pageview")
                return DiscoverFragment()
            }
            2 -> {
                presenter!!.firebaseEvent("5","Favorites pageview")
                return FavoritesFragment()
            }
            3 -> {
                presenter!!.firebaseEvent("6","Profile pageview")
                return ProfileFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}