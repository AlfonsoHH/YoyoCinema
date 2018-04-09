package com.example.alfonsohernandez.yoyocinema.domain.interactors.cache

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.database.Database
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import com.example.alfonsohernandez.yoyocinema.BuildConfig
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)

class GetCacheInfoInteractorImplTest {

    lateinit var testInteractor: GetCacheInfoInteractorImpl

    var output: List<MovieResultsItem?>? = null

    @Before
    fun setUp() {
        val context= RuntimeEnvironment.application
        val database = Database(context)
        testInteractor = GetCacheInfoInteractorImpl(database)
        val setTestInteractor = SetCacheInfoInteractorImpl(database)
        setTestInteractor.setInfoInCache("discover", arrayListOf(MovieResultsItem()))
    }

    @Test
    fun getInfoInCache_ShouldPass() {
        output = testInteractor.getInfoInCache("discover")
        assertNotEquals(output?.size, 0)
    }

    @Test
    fun getInfoInCache_ShouldNotPass() {
        output = testInteractor.getInfoInCache("discovery")
        assertEquals(output?.size, 0)
    }
}