package com.example.alfonsohernandez.yoyocinema.presentation.movie

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.AddFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.RemoveFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class MovieDetailPresenter @Inject constructor(private val getCacheInfoInteractor: GetCacheInfoInteractor, private val addFavoriteMoviesInteractor: AddFavoriteMovieInteractor, private val removeFavoriteMovieInteractor: RemoveFavoriteMovieInteractor, private val getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor, private val getUserProfileInteractor: GetUserProfileInteractor): MovieDetailContract.Presenter {

    private var view: MovieDetailContract.View? = null
    private val TAG: String = "MovieDetailPresenter"

    fun setView(view: MovieDetailContract.View?, movieID: String) {
        this.view = view
        loadCache(movieID)
        loadFirebaseData(movieID)
    }

    override fun loadCache(movieId: String){
        var movie = getCacheInfoInteractor.getInfoInCache("movie-"+movieId)
        if(movie.isNotEmpty()){
            view?.setData(movie.get(0))
        }
    }

    override fun addFavorite(movie: MovieResultsItem) {
        addFavoriteMoviesInteractor.addFirebaseDataMovie(getUserProfileInteractor.getProfile()?.firstName,movie)
    }

    override fun eraseFavorite(movieId: String) {
        removeFavoriteMovieInteractor.removeFirebaseDataMovie(getUserProfileInteractor.getProfile()?.firstName,movieId)
    }

    override fun loadFirebaseData(movieId: String) {
        view?.showProgress(true)
        /*getFavoriteMoviesInteractor.getFirebaseDataMovies(getUserProfileInteractor.getProfile()?.firstName, object : FirebaseCallback<ArrayList<MovieResultsItem>> {
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

        })*/
        getFavoriteMoviesInteractor
                .getFirebaseDataMoviesRx(getUserProfileInteractor.getProfile()?.firstName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            view?.showProgress(false)

                            var existe = false
                            var favoritesList = arrayListOf<MovieResultsItem>()
                            var movie = MovieResultsItem()

                            for (h in it.children) {
                                var movieFavFB = h.getValue(MovieResultsItem::class.java)
                                movieFavFB?.let {it2 ->
                                    if(it2!!.id.toString()==movieId){
                                        Log.d(TAG,"existe")
                                        existe=true
                                        movie = it2
                                    }
                                    favoritesList.add(it2)
                                }
                            }

                            view?.favoriteExist(existe)
                            if(existe){
                                view?.setData(movie)
                            }
                        },
                        {
                            view?.showProgress(false)
                            view?.showError()
                        }
                )
    }
}