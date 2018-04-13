package com.example.alfonsohernandez.yoyocinema.presentation.movie

import com.example.alfonsohernandez.yoyocinema.BuildConfig
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.google.firebase.FirebaseApp
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.AddFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.RemoveFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResponse
import com.example.alfonsohernandez.yoyocinema.domain.models.UserProfile
import com.example.alfonsohernandez.yoyocinema.storage.FirebaseCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import io.reactivex.Maybe.just
import io.reactivex.Single
import org.mockito.*
import org.mockito.ArgumentMatchers.anyString
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class MovieDetailActivityTest {

    @Mock
    private lateinit var getCacheInfoInteractor: GetCacheInfoInteractor

    @Mock
    private lateinit var addFavoriteMoviesInteractor: AddFavoriteMovieInteractor

    @Mock
    private lateinit var removeFavoriteMovieInteractor: RemoveFavoriteMovieInteractor

    @Mock
    private lateinit var getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor

    @Mock
    private lateinit var getUserProfileInteractor: GetUserProfileInteractor

    @Mock private lateinit var dataSnapshot: DataSnapshot

    @InjectMocks
    private lateinit var presenter: MovieDetailPresenter

    private lateinit var view: MovieDetailActivity

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        val context= RuntimeEnvironment.application

        FirebaseApp.initializeApp(context)

        val items = ArrayList<MovieResultsItem>()
        val searchKey = "test"

        items.add(Mockito.mock(MovieResultsItem::class.java))

        val user = UserProfile(null,"Alfonso",null,null, null)

        /*doReturn(user)
                .`when`(getUserProfileInteractor)
                .getProfile()*/

        `when`(getUserProfileInteractor.getProfile()).thenReturn(user)

        val mockResponse: DataSnapshot = mock()
        `when`(getFavoriteMoviesInteractor.getFirebaseDataMoviesRx("Alfonso")).thenReturn(Flowable.just(mockResponse))

        val intent = Intent().putExtra("movie", searchKey)
        view = Robolectric.buildActivity(MovieDetailActivity::class.java, intent).create().get()              //.setupActivity(MovieDetailActivity::class.java)
        //view = Robolectric.buildActivity(MovieDetailActivity::class.java).newIntent(intent).create().get()
    }

    @Test
    fun testingStart(){
        verify(presenter).setView(any(),any())
    }

    @Test
    fun setData_shouldDisplayData() {
        var movie = Mockito.mock(MovieResultsItem::class.java)

        view.setData(movie)

        //assertEquals()
    }
}