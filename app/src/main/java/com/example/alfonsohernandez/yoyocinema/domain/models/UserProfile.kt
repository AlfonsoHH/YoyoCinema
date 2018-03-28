package com.example.alfonsohernandez.yoyocinema.domain.models

import java.util.*

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
data class UserProfile(var picture: String? = null,
                       var firstName: String? = null,
                       var lastName: String? = null,
                       var email: String? = null,
                       var language: Locale? = null)