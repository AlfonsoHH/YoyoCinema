package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(AppModule::class))
class ApiModule() {

    @Provides
    @AppScope
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @AppScope
    fun providesHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @AppScope
    fun createService(okHttpClient: OkHttpClient): MoviesDBApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<MoviesDBApi>(MoviesDBApi::class.java)


        return service
    }
}