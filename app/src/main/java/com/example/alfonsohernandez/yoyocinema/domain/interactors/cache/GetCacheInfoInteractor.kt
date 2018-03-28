package com.example.alfonsohernandez.yoyocinema.domain.interactors.cache

import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
interface GetCacheInfoInteractor {
    fun getInfoInCache(key: String): List<MovieResultsItem>
}