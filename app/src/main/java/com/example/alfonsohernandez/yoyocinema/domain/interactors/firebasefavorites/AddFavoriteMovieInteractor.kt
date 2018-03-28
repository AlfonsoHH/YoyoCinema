package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
interface AddFavoriteMovieInteractor {
    fun addFirebaseDataMovie(user: String?,movie: MovieResultsItem)
}