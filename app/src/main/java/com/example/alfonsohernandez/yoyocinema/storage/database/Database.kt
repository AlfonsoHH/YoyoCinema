package com.example.alfonsohernandez.yoyocinema.storage.database

import android.content.Context
import android.util.Log
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import io.paperdb.Paper

/**
 * Created by alfonsohernandez on 26/03/2018.
 */
open class Database(context: Context) {

    private val TAG = "Database"

    init {
        Paper.init(context)
    }

    fun readData(key: String): List<MovieResultsItem>{
        try {
            return Paper.book().read(key)
        }catch(e: IllegalStateException){
            return listOf()
        }
    }

    fun addData(key: String, item: List<MovieResultsItem>){
        Paper.book().write(key,item)
    }

    fun eraseData(key: String){
        Paper.book().delete(key)
    }

    fun updateData(key: String, list: List<MovieResultsItem>){
        eraseData(key)

        addData(key,list)

    }

}