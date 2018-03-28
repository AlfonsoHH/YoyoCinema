package com.example.alfonsohernandez.yoyocinema.domain.interactors.preference

import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
interface GetUserProfileInteractor {
    fun getProfile(): UserProfile?
}