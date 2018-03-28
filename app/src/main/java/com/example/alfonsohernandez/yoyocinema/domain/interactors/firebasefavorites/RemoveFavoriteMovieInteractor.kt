package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
interface RemoveFavoriteMovieInteractor {
    fun removeFirebaseDataMovie(user: String?,movieId: String)
}