package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import android.app.Application
import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {
    @Provides
    @AppScope
    fun provideApplication() = app

    @Provides
    @AppScope
    fun provideContext(): Context = app
}