package com.example.alfonsohernandez.yoyocinema.models

import java.io.Serializable

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
data class User(val first: String, val last: String, val email: String, val picture: String) : Serializable {
    constructor() : this ("","","",""){

    }
}