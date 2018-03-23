package com.example.alfonsohernandez.yoyocinema.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class GoogleApiService() {

    fun getHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    fun createService(): GoogleApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<GoogleApi>(GoogleApi::class.java)


        return service
    }

}