package com.example.alfonsohernandez.yoyocinema.presentation.discover

import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import retrofit2.HttpException
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(private val getCacheInfoInteractor: GetCacheInfoInteractor, private val setCacheInfoInteractor: SetCacheInfoInteractor, private val getDiscoverMoviesInteractor: GetDiscoverMoviesInteractor, private val getSearchMoviesInteractor: GetSearchMoviesInteractor) : DiscoverContract.Presenter {


    private var view: DiscoverContract.View? = null

    fun setView(view: DiscoverContract.View?) {
        this.view = view
    }

    override fun loadCache(key: String): List<MovieResultsItem> {
        return getCacheInfoInteractor.getInfoInCache(key)
    }

    override fun updateCache(key: String, list: List<MovieResultsItem>) {
        setCacheInfoInteractor.setInfoInCache(key, list)
    }

    override fun loadDiscoverData() {
        getDiscoverMoviesInteractor.getDataMovies(object : GetDiscoverMoviesInteractor.getDiscoverMoviesCallback {
            override fun onSuccess(result: List<MovieResultsItem>) {
                updateCache("discover",result)
                view?.setData(result)
            }

            override fun onError(error: Throwable) {
                view?.showError()
            }

        })
    }

    override fun loadSearchData(searchString: String) {
        getSearchMoviesInteractor.getDataMovies(object : GetSearchMoviesInteractor.getSearchMoviesInterface {
            override fun onSuccess(result: List<MovieResultsItem>) {
                view?.setData(result)
            }

            override fun onError(error: Throwable) {
                view?.showError()
            }

        }, searchString)
    }

    override fun loadSearchDataOffline(key: String) {
        var list = loadCache("discover")
        view?.setData(list.filter { it.title!!.contains(key) })
    }

}