package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface GetFavoriteMoviesInteractor {
    fun getFirebaseDataMovies(user: String?, firebaseCallback: FirebaseCallback<ArrayList<MovieResultsItem>>)
    fun getFirebaseDataMoviesRx(user: String?): Flowable<DataSnapshot>
}