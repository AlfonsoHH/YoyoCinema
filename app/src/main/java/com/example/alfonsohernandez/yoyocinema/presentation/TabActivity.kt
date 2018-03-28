package com.example.alfonsohernandez.yoyocinema.presentation

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterTabPager
import android.support.design.widget.TabLayout
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import kotlinx.android.synthetic.main.activity_tab.*
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.alfonsohernandez.yoyocinema.R
import com.google.firebase.analytics.FirebaseAnalytics
import dk.nodes.nstack.kotlin.inflater.NStackBaseContext


class TabActivity : AppCompatActivity() {

    var  mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        setSupportActionBar(toolbarTab)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);

        configureTabLayout()

    }

    private fun configureTabLayout() {

        tab_layout.addTab(tab_layout.newTab().setText("Map"))
        tab_layout.addTab(tab_layout.newTab().setText("Discover"))
        tab_layout.addTab(tab_layout.newTab().setText("Favorites"))
        tab_layout.addTab(tab_layout.newTab())


        val pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)


        Glide.with(this).asBitmap().load(pref.getString("picture","")).into(object : SimpleTarget<Bitmap>(60,60) {

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val roundedDrawable = RoundedBitmapDrawableFactory.create(applicationContext.resources, resource)
                roundedDrawable.isCircular = true
                tab_layout.getTabAt(3)!!.setIcon(roundedDrawable)          }
        })

        val adapter = AdapterTabPager(supportFragmentManager,
                tab_layout.tabCount,mFirebaseAnalytics!!)
        pager.adapter = adapter

        pager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(NStackBaseContext(newBase))
    }

}