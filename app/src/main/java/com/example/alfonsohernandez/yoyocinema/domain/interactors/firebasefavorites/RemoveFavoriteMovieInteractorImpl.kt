package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

import com.example.alfonsohernandez.yoyocinema.domain.repository.FavoriteMoviesRepository

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class RemoveFavoriteMovieInteractorImpl(private val favoriteMoviesRepository: FavoriteMoviesRepository): RemoveFavoriteMovieInteractor {
    override fun removeFirebaseDataMovie(user: String?, movieId: String) {
        favoriteMoviesRepository.removeFavoriteMovie(user,movieId)
    }
}