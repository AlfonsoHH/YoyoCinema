package com.example.alfonsohernandez.yoyocinema.storage.database

import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import io.paperdb.Paper

class UserProfileDatabase(context: Context) {

    private val TAG = "UserProfileDatabase"
    private val KEY_USER_PROFILE = "KEY_USER_PROFILE"

    init {
        Paper.init(context)
    }

    fun get(): UserProfile? {
        return Paper.book().read(KEY_USER_PROFILE, null)
    }

    fun set(value: UserProfile?) {
        Paper.book().write(KEY_USER_PROFILE, value)
    }

    fun clear() {
        Paper.book().delete(KEY_USER_PROFILE)
    }

}