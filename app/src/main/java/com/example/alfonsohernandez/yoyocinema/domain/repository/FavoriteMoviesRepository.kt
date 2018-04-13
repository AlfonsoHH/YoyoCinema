package com.example.alfonsohernandez.yoyocinema.domain.repository

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by alfonsohernandez on 26/03/2018.
 */

class FavoriteMoviesRepository {
    val firebaseInstance = FirebaseDatabase.getInstance().getReference("favorites")

    fun addFavoriteMovie(user: String?, movie: MovieResultsItem){
        val userInstance = firebaseInstance.child(user)
        userInstance.child(movie.id.toString()).setValue(movie).addOnCompleteListener { println("Data added") }
    }

    fun removeFavoriteMovie(user: String?,movieId: String){
        val userInstance = firebaseInstance.child(user)
        userInstance.child(movieId).removeValue()
    }

    fun getMoviesForUser(userId: String?, callback: FirebaseCallback<ArrayList<MovieResultsItem>>) {
        val userInstance = firebaseInstance.child(userId)

        userInstance.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onError(error)
            }

            override fun onDataChange(p0: DataSnapshot?) {

                var favoritesList = arrayListOf<MovieResultsItem>()

                for (h in p0!!.children) {
                    var movieFavFB = h.getValue(MovieResultsItem::class.java)

                    movieFavFB?.let {
                        favoritesList.add(it)
                    }

                }
                callback.onDataReceived(favoritesList)
            }
        })
    }

    fun getMoviesForUserRx(userId: String?): Flowable<DataSnapshot> {
        return RxFirebaseDatabase.observeValueEvent(firebaseInstance.child(userId))
    }
}