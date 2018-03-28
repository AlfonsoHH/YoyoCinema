package com.example.alfonsohernandez.yoyocinema.domain.injection.components

import android.app.Application
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.*
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import dagger.Component
import javax.inject.Singleton

/**
 * Created by alfonsohernandez on 26/03/2018.
 */

@AppScope
@Component(modules = arrayOf(
        AppModule::class,
        InteractorModule::class,
        ApiModule::class,
        DatabaseModule::class,
        PreferenceModule::class,
        RepositoryModule::class
))
interface AppComponent {
    fun inject(app: Application)
    fun plus(presentationModule: PresentationModule): PresentationComponent
}