package com.example.alfonsohernandez.yoyocinema.domain.interactors.cache

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.storage.database.Database

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
class SetCacheInfoInteractorImpl(private val database: Database): SetCacheInfoInteractor {
    override fun setInfoInCache(key: String, item: List<MovieResultsItem>) {
        database.updateData(key,item)
    }
}