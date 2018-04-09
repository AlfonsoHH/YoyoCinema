package com.example.alfonsohernandez.yoyocinema.presentation.discover

import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class DiscoverPresenterTest {

    /*private val view: DiscoverFragment = mock()
    private val getCacheInfo: GetCacheInfoInteractor = mock()
    private val setCacheInfo: SetCacheInfoInteractor = mock()
    private val getDiscover: GetDiscoverMoviesInteractor = mock()
    private val getSearch: GetSearchMoviesInteractor = mock()*/

    @Mock
    private lateinit var view: DiscoverFragment

    @Mock
    private lateinit var getCacheInfo: GetCacheInfoInteractor

    @Mock
    private lateinit var setCacheInfo: SetCacheInfoInteractor

    @Mock
    private lateinit var getDiscover: GetDiscoverMoviesInteractor

    @Mock
    private lateinit var getSearch: GetSearchMoviesInteractor

    private lateinit var presenter: DiscoverPresenter
    //private lateinit var testScheduler: TestScheduler

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        //when(GetDiscoverMoviesInteractor.getDiscoverMoviesCallback).thenReturn(Observable.just(arrayListOf(MovieResultsItem)))
        presenter = DiscoverPresenter(getCacheInfo,setCacheInfo,getDiscover,getSearch)
        presenter.setView(view)
    }

    @Test
    fun setView() {
    }

    @Test
    fun loadCache() {
    }

    @Test
    fun updateCache() {
    }

    @Test
    fun loadDiscoverDataRxJava() {

        presenter.loadDiscoverDataRxJava()

        verify(presenter, times(1)).loadDiscoverDataRxJava()

    }

    @Test
    fun loadSearchDataRxJava() {
    }
}