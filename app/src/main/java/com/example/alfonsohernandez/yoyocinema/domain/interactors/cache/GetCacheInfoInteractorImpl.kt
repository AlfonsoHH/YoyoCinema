package com.example.alfonsohernandez.yoyocinema.domain.interactors.cache

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.database.Database

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class GetCacheInfoInteractorImpl(private val database: Database) : GetCacheInfoInteractor {
    override fun getInfoInCache(key: String): List<MovieResultsItem> {
        return database.readData(key)
    }
}