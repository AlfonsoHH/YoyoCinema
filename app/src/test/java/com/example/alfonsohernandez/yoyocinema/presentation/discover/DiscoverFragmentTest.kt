package com.example.alfonsohernandez.yoyocinema.presentation.discover

import android.support.v7.widget.LinearLayoutManager
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import android.support.v7.widget.RecyclerView
import android.widget.Adapter
import com.example.alfonsohernandez.yoyocinema.BuildConfig
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterMovies
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import kotlinx.android.synthetic.main.fragment_discover.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class DiscoverFragmentTest{

    @Mock
    private lateinit var presenter: DiscoverPresenter

    @Mock
    private lateinit var recyclerView: RecyclerView

    @Mock
    private lateinit var adapter: AdapterMovies

    private lateinit var view: DiscoverFragment

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        val context= RuntimeEnvironment.application

        view = DiscoverFragment()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.onMovieClickedListener = { movie ->
            view.goToMovieDetail(movie)
        }

        presenter.setView(view)
    }

    @Test
    fun setData_shouldRecieveData(){
        var items = ArrayList<MovieResultsItem>()
        items.add(Mockito.mock(MovieResultsItem::class.java))

        view.setData(items)

        //verify(adapter).ViewHolder(any()).bindItems(any())
        //assertEquals(1,adapter.movieList.size)
    }

    @Test
    fun getUserClick(){
        val items = ArrayList<MovieResultsItem>()
        items.add(Mockito.mock(MovieResultsItem::class.java))

        view.setData(items)

        recyclerView.getChildAt(0).performClick()

        verify(view).goToMovieDetail(Mockito.mock(MovieResultsItem::class.java))
    }

}