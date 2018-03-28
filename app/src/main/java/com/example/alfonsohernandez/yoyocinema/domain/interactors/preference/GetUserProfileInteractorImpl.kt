package com.example.alfonsohernandez.yoyocinema.domain.interactors.preference

import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.example.alfonsohernandez.yoyocinema.storage.database.Database
import com.example.alfonsohernandez.yoyocinema.storage.database.UserProfileDatabase
import com.example.alfonsohernandez.yoyocinema.storage.preferences.MyPreferencesManager
import com.google.gson.Gson
import io.paperdb.Paper

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class GetUserProfileInteractorImpl(private val database: UserProfileDatabase) : GetUserProfileInteractor {
    override fun getProfile(): UserProfile? {
        return database.get()
    }

}