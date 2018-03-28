package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import com.example.alfonsohernandez.yoyocinema.storage.database.Database
import com.example.alfonsohernandez.yoyocinema.storage.database.UserProfileDatabase
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(AppModule::class))
class DatabaseModule {
    @Provides
    @AppScope
    fun providesDatabase(context: Context): Database{
        return Database(context)
    }

    @Provides
    @AppScope
    fun providesDatabaseUserProfile(context: Context): UserProfileDatabase{
        return UserProfileDatabase(context)
    }
}