package com.example.alfonsohernandez.yoyocinema.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class ApiService() {

    fun getHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    fun createService(): Api {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<Api>(Api::class.java)


        return service
    }

}