package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import com.example.alfonsohernandez.yoyocinema.storage.preferences.MyPreferencesManager
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(AppModule::class))
class PreferenceModule {
    @Provides
    @AppScope
    fun providesPreferences(context: Context): MyPreferencesManager{
        return MyPreferencesManager(context)
    }
}