package com.example.alfonsohernandez.yoyocinema

import android.app.Application
import com.example.alfonsohernandez.yoyocinema.domain.injection.components.AppComponent
import com.example.alfonsohernandez.yoyocinema.domain.injection.components.DaggerAppComponent
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.AppModule
import com.example.alfonsohernandez.yoyocinema.domain.models.Translation
import dk.nodes.nstack.kotlin.NStack
import javax.inject.Inject


/**
 * Created by alfonsohernandez on 20/03/2018.
 */
class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        lateinit var instance: App
    }

    @Inject
    lateinit var app : Application

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        instance = this

        NStack.debugMode = true
        NStack.translationClass = Translation::class.java
        NStack.init(this)

    }

}