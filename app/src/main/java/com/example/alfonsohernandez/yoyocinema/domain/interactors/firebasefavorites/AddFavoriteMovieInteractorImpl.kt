package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.repository.FavoriteMoviesRepository

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class AddFavoriteMovieInteractorImpl(private val favoriteMoviesRepository: FavoriteMoviesRepository): AddFavoriteMovieInteractor {
    override fun addFirebaseDataMovie(user: String?, movie: MovieResultsItem) {
        favoriteMoviesRepository.addFavoriteMovie(user,movie)
    }
}