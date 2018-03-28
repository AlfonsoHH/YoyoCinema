package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.AppScope
import com.example.alfonsohernandez.yoyocinema.domain.repository.FavoriteMoviesRepository
import com.example.alfonsohernandez.yoyocinema.storage.database.Database
import dagger.Module
import dagger.Provides

/**
 * Created by alfonsohernandez on 27/03/2018.
 */
@Module
class RepositoryModule {
    @Provides
    @AppScope
    fun providesFavoriteMovieRepository(): FavoriteMoviesRepository {
        return FavoriteMoviesRepository()
    }
}