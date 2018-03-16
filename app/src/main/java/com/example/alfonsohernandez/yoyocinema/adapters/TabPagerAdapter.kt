package com.example.alfonsohernandez.yoyocinema.adapters

/**
 * Created by alfonsohernandez on 16/03/2018.
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.alfonsohernandez.yoyocinema.Tab1Fragment
import com.example.alfonsohernandez.yoyocinema.Tab2Fragment
import com.example.alfonsohernandez.yoyocinema.Tab3Fragment
import com.example.alfonsohernandez.yoyocinema.Tab4Fragment

class TabPagerAdapter(fm: FragmentManager, private var tabCount: Int) :
        FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> return Tab1Fragment()
            1 -> return Tab2Fragment()
            2 -> return Tab3Fragment()
            3 -> return Tab4Fragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}