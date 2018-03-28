package com.example.alfonsohernandez.yoyocinema.storage

import com.google.firebase.database.DatabaseError

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
interface FirebaseCallback<T> {
    fun onDataReceived(data: T)
    fun onError(error: DatabaseError)
}