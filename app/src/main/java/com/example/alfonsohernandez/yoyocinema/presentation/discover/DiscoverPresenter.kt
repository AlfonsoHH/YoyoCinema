package com.example.alfonsohernandez.yoyocinema.presentation.discover

import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class DiscoverPresenter @Inject constructor(private val getCacheInfoInteractor: GetCacheInfoInteractor, private val setCacheInfoInteractor: SetCacheInfoInteractor, private val getDiscoverMoviesInteractor: GetDiscoverMoviesInteractor, private val getSearchMoviesInteractor: GetSearchMoviesInteractor) : DiscoverContract.Presenter {

    private var view: DiscoverContract.View? = null

    fun setView(view: DiscoverContract.View?) {
        this.view = view
        loadCache("discover")
        loadDiscoverDataRxJava()
    }

    override fun loadCache(key: String) {
        view?.setData(getCacheInfoInteractor.getInfoInCache(key))
    }

    override fun updateCache(key: String, list: List<MovieResultsItem>?) {
        setCacheInfoInteractor.setInfoInCache(key, list!!)
    }

    override fun loadDiscoverData() {
        view?.showProgress(true)
        getDiscoverMoviesInteractor.getDataMovies(object : GetDiscoverMoviesInteractor.getDiscoverMoviesCallback {
            override fun onSuccess(result: List<MovieResultsItem>) {
                view?.showProgress(false)
                updateCache("discover", result)
                view?.setData(result)
            }

            override fun onError(error: Throwable) {
                view?.showProgress(false)
                view?.showError()
            }

        })
    }

    override fun loadDiscoverDataRxJava() {
        view?.showProgress(true)
        getDiscoverMoviesInteractor
                .getDataMoviesRxJava()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   view?.showProgress(false)
                            updateCache("discover", it.results)
                            view?.setData(it.results)
                        },
                        {   view?.showProgress(false)
                            view?.showError()
                        }
                )
    }

    override fun loadSearchData(searchString: String) {
        view?.showProgress(true)
        getSearchMoviesInteractor.getDataMovies(object : GetSearchMoviesInteractor.getSearchMoviesInterface {
            override fun onSuccess(result: List<MovieResultsItem>) {
                view?.showProgress(false)
                updateCache("search-" + searchString, result)
                view?.setData(result)
            }

            override fun onError(error: Throwable) {
                view?.showProgress(false)
                view?.showError()
            }

        }, searchString)
    }

    override fun loadSearchDataRxJava(searchString: String) {
        view?.showProgress(true)
        getSearchMoviesInteractor
                .getDataMoviesRxJava(searchString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   view?.showProgress(false)
                            updateCache("search-" + searchString, it.results)
                            view?.setData(it.results)
                        },
                        {   view?.showProgress(false)
                            view?.showError()
                        }
                )
    }

}