package com.example.alfonsohernandez.yoyocinema.presentation.tabs

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterTabPager
import android.support.design.widget.TabLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_tab.*
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.Toast
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import dk.nodes.nstack.kotlin.inflater.NStackBaseContext
import javax.inject.Inject


class TabActivity : AppCompatActivity(), TabContract.View{

    @Inject
    lateinit var presenter: TabPresenter

    private val TAG = "TabActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        setSupportActionBar(toolbarTab)

        injectDependencies()
        presenter.setView(this)

        setTabLayout()

    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(NStackBaseContext(newBase))
    }

    override fun setData(picture: String) {

        tab_layout.addTab(tab_layout.newTab().setText("Map"))
        tab_layout.addTab(tab_layout.newTab().setText("Discover"))
        tab_layout.addTab(tab_layout.newTab().setText("Favorites"))
        tab_layout.addTab(tab_layout.newTab())

        Glide.with(this).asBitmap().load(picture).into(object : SimpleTarget<Bitmap>(60,60) {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val roundedDrawable = RoundedBitmapDrawableFactory.create(applicationContext.resources, resource)
                roundedDrawable.isCircular = true
                tab_layout.getTabAt(3)!!.setIcon(roundedDrawable)          }
        })

    }

    override fun showProgress(isLoading: Boolean) {
        println("Showing progress")
    }

    override fun showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    private fun setTabLayout() {
        val adapter = AdapterTabPager(supportFragmentManager,
                tab_layout.tabCount,
                presenter)

        pager.adapter = adapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}