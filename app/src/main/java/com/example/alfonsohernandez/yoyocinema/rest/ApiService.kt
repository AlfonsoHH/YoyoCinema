package com.example.alfonsohernandez.yoyocinema.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class ApiService() {

    fun createService(): Api {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/550?api_key=c9221bf28759d13b63e8730e5af4a329")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<Api>(Api::class.java)
        return service
    }

}