package com.example.alfonsohernandez.yoyocinema.presentation.map

import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces.GetGoogleMapNearbyPlacesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.ResultsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by alfonsohernandez on 28/03/2018.
 */
class MapPresenter @Inject constructor(private val getGoogleMapNearbyPlacesInteractor: GetGoogleMapNearbyPlacesInteractor) : MapContract.Presenter {

    private var view: MapContract.View? = null

    private val TAG = "MapPresenter"

    fun setView(view: MapContract.View?) {
        this.view = view
    }

    override fun loadSearchData(searchString: String, position: String, radius: Int) {
        view?.showProgress(true)

        //Log.d(TAG,searchString+" "+position+" "+radius.toString())

        getGoogleMapNearbyPlacesInteractor
                .getNearbyPlaces(searchString, position, radius)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {   view?.showProgress(false)
                            view?.setData(it.results)
                        },
                        {   view?.showProgress(false)
                            view?.showError()
                        }
                )
    }
}