package com.example.alfonsohernandez.yoyocinema.storage.database

import android.content.Context
import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.paperdb.Paper

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
class Database(context: Context) {

    private val TAG = "Database"

    init {
        Paper.init(context)
    }

    fun readData(key: String): List<MovieResultsItem>{
        //var movie:MovieResultsItem = Paper.book().read(key)
        //Log.d(TAG,movie.title)
        val list: List<MovieResultsItem> = Paper.book().read(key)

        return list
    }

    fun addData(key: String, item: MovieResultsItem){
        Paper.book().write(key,item)
    }

    fun eraseData(key: String){
        Paper.book().delete(key)
    }

    fun updateData(key: String, list: List<MovieResultsItem>){
        eraseData(key)

        Paper.book().write(key,list)

    }

}