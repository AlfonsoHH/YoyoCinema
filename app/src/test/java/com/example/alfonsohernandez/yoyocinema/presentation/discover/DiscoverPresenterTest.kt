package com.example.alfonsohernandez.yoyocinema.presentation.discover

import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.schedulers.Schedulers

import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.*

class DiscoverPresenterTest {

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

    //private lateinit var mockResponse: MovieResponse

    private lateinit var presenter: DiscoverPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        val mockResponse: MovieResponse = mock()

        `when`(getDiscover.getDataMoviesRxJava()).thenReturn(just(mockResponse))

        presenter = DiscoverPresenter(getCacheInfo,setCacheInfo,getDiscover,getSearch)
        presenter.setView(view)
    }

    @Test
    fun setView_ShouldPass() {
        val mockResponse: MovieResponse = mock()

        verify(getCacheInfo, atLeastOnce()).getInfoInCache(ArgumentMatchers.anyString())
        verify(getDiscover, atLeastOnce()).getDataMoviesRxJava()
        verify(view, times(1)).showProgress(true)
        verify(view, times(2)).setData(mockResponse.results)
        verify(view, times(1)).showProgress(false)
    }

    @Test
    fun loadSearchDataRxJava_should_call() {
        val mockResponse: MovieResponse = mock()

        doReturn(just(mockResponse))
                .`when`(getSearch)
                .getDataMoviesRxJava(ArgumentMatchers.anyString())

        presenter.loadSearchDataRxJava(ArgumentMatchers.anyString())

        verify(getSearch, atLeastOnce()).getDataMoviesRxJava(ArgumentMatchers.anyString())
        verify(view, times(2)).showProgress(true)
        verify(view, times(3)).setData(mockResponse.results)
        verify(view, times(2)).showProgress(false)
    }

    @Test
    fun loadSearchDataRxJava_shouldNot_callNoResult() {
        val mockedResponse: MovieResponse = mock()
        val items = ArrayList<MovieResultsItem>()
        val searchKey = "test"

        items.add(Mockito.mock(MovieResultsItem::class.java))

        `when`(mockedResponse.results).thenReturn(items)

        doReturn(just(mockedResponse))
                .`when`(getSearch)
                .getDataMoviesRxJava(searchKey)

        presenter.loadSearchDataRxJava(searchKey)

        verify(view, times(0)).noResult()
    }

    @Test
    fun loadSearchDataRxJava_should_callNoResult() {
        val mockedResponse: MovieResponse = mock()
        val items = ArrayList<MovieResultsItem>()
        val searchKey = "test"

        `when`(mockedResponse.results).thenReturn(items)

        doReturn(just(mockedResponse))
                .`when`(getSearch)
                .getDataMoviesRxJava(searchKey)

        presenter.loadSearchDataRxJava(searchKey)

        verify(view).noResult()
    }

    @Test
    fun loadSearchDataRxJava_ShouldCallError() {
        var mockResponseEror: Throwable = mock()
        val searchKey = "test"

        `when`(getSearch.getDataMoviesRxJava(searchKey)).thenReturn(Single.error(mockResponseEror))

        presenter.loadSearchDataRxJava(searchKey)

        verify(view, atLeastOnce()).showError()
    }

    @After
    fun tearDown(){
        presenter.setView(null)
    }
}