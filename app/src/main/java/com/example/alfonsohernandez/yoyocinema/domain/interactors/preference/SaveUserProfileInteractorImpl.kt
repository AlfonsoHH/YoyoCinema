package com.example.alfonsohernandez.yoyocinema.domain.interactors.preference

import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.example.alfonsohernandez.yoyocinema.storage.database.UserProfileDatabase
import com.example.alfonsohernandez.yoyocinema.storage.preferences.MyPreferencesManager

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class SaveUserProfileInteractorImpl(private val database: UserProfileDatabase) : SaveUserProfileInteractor {
    override fun save(userProfile: UserProfile?) {
        database.set(userProfile)
    }
}