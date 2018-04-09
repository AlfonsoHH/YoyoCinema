package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApi
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alfonsohernandez on 03/04/2018.
 */
@Module(includes = arrayOf(AppModule::class))
class GoogleApiModule() {

    @Provides
    @AppScope
    fun createService(okHttpClient: OkHttpClient): GoogleApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val service = retrofit.create<GoogleApi>(GoogleApi::class.java)


        return service
    }
}