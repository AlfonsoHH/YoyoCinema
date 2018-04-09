package com.example.alfonsohernandez.yoyocinema.presentation.favorites

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback
import com.google.firebase.database.DatabaseError
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class FavoritesPresenter @Inject constructor(private val getCacheInfoInteractor: GetCacheInfoInteractor,
                                             private val setCacheInfoInteractor: SetCacheInfoInteractor,
                                             private val getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor,
                                             private val GetUserProfileInteractor: GetUserProfileInteractor
): FavoritesContract.Presenter {

    private var view: FavoritesContract.View? = null

    private val TAG = "FavoritesPresenter"


    fun setView(view: FavoritesContract.View?) {
        this.view = view
        loadCache()
        loadFirebaseData()
    }

    override fun loadCache(): List<MovieResultsItem> {
        val list = getCacheInfoInteractor.getInfoInCache("user-"+ GetUserProfileInteractor.getProfile()?.firstName)
        view?.setData(list)
        return list
    }

    override fun updateUserCache(list: List<MovieResultsItem>) {
        setCacheInfoInteractor.setInfoInCache("user-"+ GetUserProfileInteractor.getProfile()?.firstName, list)
    }

    override fun updateCache(key: String, list: List<MovieResultsItem>) {
        setCacheInfoInteractor.setInfoInCache(key,list)
    }

    override fun loadSearchData(searchString: String) {
        getFavoriteMoviesInteractor.getFirebaseDataMovies(GetUserProfileInteractor.getProfile()?.firstName,
                object : FirebaseCallback<ArrayList<MovieResultsItem>> {

            override fun onDataReceived(data: ArrayList<MovieResultsItem>) {
                view?.showProgress(false)
                val dataVar = data.filter { it.title!!.contains(searchString)  }
                view?.setData(dataVar)
            }
            override fun onError(error: DatabaseError) {
                view?.showProgress(false)
                view?.showError()
            }
        })
    }

    override fun loadFirebaseData() {
        view?.showProgress(true)
        getFavoriteMoviesInteractor.getFirebaseDataMovies(GetUserProfileInteractor.getProfile()?.firstName, object : FirebaseCallback<ArrayList<MovieResultsItem>> {
            override fun onDataReceived(data: ArrayList<MovieResultsItem>) {
                view?.showProgress(false)
                updateUserCache(data)
                view?.setData(data)
            }

            override fun onError(error: DatabaseError) {
                view?.showProgress(false)
                view?.showError()
            }

        })

    }

    override fun loadSearchDataOffline(searchString: String) {
        var list = loadCache()
        view?.setData(list.filter { it.title!!.contains(searchString) })
    }

}