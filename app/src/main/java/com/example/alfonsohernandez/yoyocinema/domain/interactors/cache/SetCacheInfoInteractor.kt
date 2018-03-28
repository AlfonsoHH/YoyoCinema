package com.example.alfonsohernandez.yoyocinema.domain.interactors.cache

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface SetCacheInfoInteractor {
    fun setInfoInCache(key: String, item: List<MovieResultsItem>)
}