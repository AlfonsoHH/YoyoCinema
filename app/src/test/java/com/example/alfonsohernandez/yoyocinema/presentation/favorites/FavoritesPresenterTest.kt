package com.example.alfonsohernandez.yoyocinema.presentation.favorites

import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.google.firebase.database.DataSnapshot
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class FavoritesPresenterTest {

    @Mock
    private lateinit var getCache: GetCacheInfoInteractor

    @Mock
    private lateinit var setCache: SetCacheInfoInteractor

    @Mock
    private lateinit var getFavorites: GetFavoriteMoviesInteractor

    @Mock
    private lateinit var getUserProfile: GetUserProfileInteractor

    private lateinit var presenter: FavoritesPresenter

    @Mock
    private lateinit var view: FavoritesFragment

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        doReturn(UserProfile(null,"Alfonso",null,null, null))
                .`when`(getUserProfile)
                .getProfile()

        val mockResponse: DataSnapshot = mock()
        `when`(getFavorites.getFirebaseDataMoviesRx(anyString())).thenReturn(Flowable.just(mockResponse))

        presenter = FavoritesPresenter(getCache,setCache,getFavorites,getUserProfile)
        presenter.setView(view)
    }

    @Test
    fun setView_shouldPass() {
        val items = listOf<MovieResultsItem>()

        verify(getCache, Mockito.atLeastOnce()).getInfoInCache(anyString())
        verify(getFavorites, Mockito.atLeastOnce()).getFirebaseDataMoviesRx(anyString())
        verify(view, times(1)).showProgress(true)
        verify(view, times(1)).setData(items)
        verify(view, times(1)).showProgress(false)

    }

    @Test
    fun loadSearchData_Should_Call() {
        val items = arrayListOf<MovieResultsItem>()
        val mockResponse: DataSnapshot = mock()
        val searchKey = "test"

        doReturn(Flowable.just(mockResponse))
                .`when`(getFavorites)
                .getFirebaseDataMoviesRx(ArgumentMatchers.anyString())

        presenter.loadSearchData(searchKey)

        verify(getFavorites, Mockito.atLeastOnce()).getFirebaseDataMoviesRx("Alfonso")
        verify(view, times(1)).showProgress(true)
        verify(view, times(2)).setData(items)
        verify(view, times(2)).showProgress(false)
    }

    @Test
    fun loadSearchData_ShouldCallError() {
        var mockResponseEror: Throwable = mock()
        val searchKey = "test"

        `when`(getFavorites.getFirebaseDataMoviesRx(searchKey)).thenReturn(Flowable.error(mockResponseEror))

        presenter.loadSearchData(searchKey)

        verify(view, Mockito.atLeastOnce()).showError()
    }

    @Test
    fun loadSearchDataOffline_ShouldCall_Results() {
        val searchKey = "123"
        val movie = MovieResultsItem(null,null,null,null,null,null,null,null,null,null,null,searchKey.toInt(),null,null)
        val items = listOf(movie,movie)

        doReturn(items)
                .`when`(getCache)
                .getInfoInCache("Alfonso")

        presenter.loadSearchDataOffline(searchKey)

        verify(view, never()).showNoResults()
    }

    @Test
    fun loadSearchDataOffline_ShouldCall_NotResults() {
        val searchKey = "123"
        val items = listOf(MovieResultsItem(null,null,null,null,null,null,null,null,null,null,null,124,null,null))

        doReturn(items)
                .`when`(getCache)
                .getInfoInCache("Alfonso")

        presenter.loadSearchDataOffline(searchKey)

        verify(view).showNoResults()
    }

}