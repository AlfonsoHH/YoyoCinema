package com.example.alfonsohernandez.yoyocinema.POJOMovie

/**
 * Created by alfonsohernandez on 19/03/2018.
 */
import java.io.Serializable

data class MovieFav(

        val id: String? = null,
        val title: String? = null,
        val overview: String? = null,
        val posterPath: String? = null,
        val releaseDate: String? = null,
        val voteAverage: Double? = null,
        val user: String? =null

) : Serializable