package com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.repository.FavoriteMoviesRepository
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class GetFavoriteMoviesInteractorImpl(private val favoriteMoviesRepository: FavoriteMoviesRepository) : GetFavoriteMoviesInteractor {
    override fun getFirebaseDataMovies(user: String?, firebaseCallback: FirebaseCallback<ArrayList<MovieResultsItem>>) {
        favoriteMoviesRepository.getMoviesForUser(user, firebaseCallback)
    }
}