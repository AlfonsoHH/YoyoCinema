package com.example.alfonsohernandez.yoyocinema.presentation.map

import com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces.GetGoogleMapNearbyPlacesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.atLeastOnce
import org.mockito.MockitoAnnotations

class MapPresenterTest {

    @Mock
    private lateinit var view: MapFragment

    @Mock
    private lateinit var getMap: GetGoogleMapNearbyPlacesInteractor

    //private lateinit var mockResponse: MovieResponse

    private lateinit var presenter: MapPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = MapPresenter(getMap)
        presenter.setView(view)
    }

    @Test
    fun loadSearchData_ShouldCall() {
        val mockResponse: Response = mock()
        var type = "test"
        var pos = "1,2"
        var radius = 10000

        `when`(getMap.getNearbyPlaces(type,pos,radius)).thenReturn(just(mockResponse))

        presenter.loadSearchData(type,pos,radius)

        verify(getMap, atLeastOnce()).getNearbyPlaces(type,pos,radius)
        verify(view, times(1)).showProgress(true)
        verify(view, times(1)).setData(mockResponse.results)
        verify(view, times(1)).showProgress(false)
    }

    @Test
    fun loadSearchData_ShouldError() {
        val mockResponse: Throwable = mock()
        var type = "test"
        var pos = "1,2"
        var radius = 10000

        `when`(getMap.getNearbyPlaces(type,pos,radius)).thenReturn(Single.error(mockResponse))

        presenter.loadSearchData(type,pos,radius)

        verify(view).showError()
    }
}