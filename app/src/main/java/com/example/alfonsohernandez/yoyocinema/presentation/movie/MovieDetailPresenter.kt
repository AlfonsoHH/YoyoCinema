package com.example.alfonsohernandez.yoyocinema.presentation.movie

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.AddFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.RemoveFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback
import com.google.firebase.database.DatabaseError
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class MovieDetailPresenter @Inject constructor(private val getCacheInfoInteractor: GetCacheInfoInteractor, private val addFavoriteMoviesInteractor: AddFavoriteMovieInteractor, private val removeFavoriteMovieInteractor: RemoveFavoriteMovieInteractor, private val getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor, private val getUserProfileInteractor: GetUserProfileInteractor): MovieDetailContract.Presenter {

    private var view: MovieDetailContract.View? = null
    private val TAG: String = "MovieDetailPresenter"

    fun setView(view: MovieDetailContract.View?) {
        this.view = view
    }

    override fun loadCache(movieId: String){
        view?.setData(getCacheInfoInteractor.getInfoInCache("movie-"+movieId).get(0))
    }

    override fun addFavorite(movie: MovieResultsItem) {
        addFavoriteMoviesInteractor.addFirebaseDataMovie(getUserProfileInteractor.getProfile()?.firstName,movie)
    }

    override fun eraseFavorite(movieId: String) {
        removeFavoriteMovieInteractor.removeFirebaseDataMovie(getUserProfileInteractor.getProfile()?.firstName,movieId)
    }

    override fun loadFirebaseData(movieId: String) {
        view?.showProgress(true)
        getFavoriteMoviesInteractor.getFirebaseDataMovies(getUserProfileInteractor.getProfile()?.firstName, object : FirebaseCallback<ArrayList<MovieResultsItem>> {
            override fun onDataReceived(data: ArrayList<MovieResultsItem>) {
                view?.showProgress(false)
                var existe = false
                var movie = MovieResultsItem()
                for (item in data){
                    if(item.id==movieId.toInt()){
                        existe=true
                        movie = item
                    }
                }
                view?.favoriteExist(existe)
                if(existe){
                    view?.setData(movie)
                }
            }
            override fun onError(error: DatabaseError) {
                view?.showProgress(false)
                view?.showError()
            }

        })
    }
}