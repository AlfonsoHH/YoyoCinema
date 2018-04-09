package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import android.content.Context
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.*
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.NewUseFirebaseAnalyticsInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.NewUseFirebaseAnalyticsInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.*
import com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces.GetGoogleMapNearbyPlacesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces.GetGoogleMapNearbyPlacesInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.GetNStackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.GetNStackLanguageInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractorImpl
import com.example.alfonsohernandez.yoyocinema.domain.repository.FavoriteMoviesRepository
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApi
import com.example.alfonsohernandez.yoyocinema.network.rest.MoviesDBApi
import com.example.alfonsohernandez.yoyocinema.storage.database.Database
import com.example.alfonsohernandez.yoyocinema.storage.database.UserProfileDatabase
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(DatabaseModule::class,ApiModule::class))
class InteractorModule {
    @Provides
    fun providesGetDiscoverMoviesInteractorImpl(moviesDBApi: MoviesDBApi): GetDiscoverMoviesInteractor {
        return GetDiscoverMoviesInteractorImpl(moviesDBApi)
    }

    @Provides
    fun providesGetSearchMoviesInteractorImpl(moviesDBApi: MoviesDBApi): GetSearchMoviesInteractor {
        return GetSearchMoviesInteractorImpl(moviesDBApi)
    }

    @Provides
    fun providesGetCacheInfoInteractorImpl(database: Database): GetCacheInfoInteractor {
        return GetCacheInfoInteractorImpl(database)
    }

    @Provides
    fun providesSetCacheInfoInteractorImpl(database: Database): SetCacheInfoInteractor {
        return SetCacheInfoInteractorImpl(database)
    }

    @Provides
    fun providesGetUserProfileInteractorImpl(database: UserProfileDatabase): GetUserProfileInteractor {
        return GetUserProfileInteractorImpl(database)
    }

    @Provides
    fun providesSaveUserProfileInteractorInteractorImpl(database: UserProfileDatabase): SaveUserProfileInteractor {
        return SaveUserProfileInteractorImpl(database)
    }

    @Provides
    fun providesGetFavoriteMoviesInteractorImpl(favoriteMoviesRepository: FavoriteMoviesRepository): GetFavoriteMoviesInteractor {
        return GetFavoriteMoviesInteractorImpl(favoriteMoviesRepository)
    }

    @Provides
    fun providesAddFavoriteMovieInteractorImpl(favoriteMoviesRepository: FavoriteMoviesRepository): AddFavoriteMovieInteractor {
        return AddFavoriteMovieInteractorImpl(favoriteMoviesRepository)
    }

    @Provides
    fun providesRemoveFavoriteMovieInteractorImpl(favoriteMoviesRepository: FavoriteMoviesRepository): RemoveFavoriteMovieInteractor {
        return RemoveFavoriteMovieInteractorImpl(favoriteMoviesRepository)
    }

    @Provides
    fun providesGetNStackLanguageInteractorImpl(): GetNStackLanguageInteractor {
        return GetNStackLanguageInteractorImpl()
    }

    @Provides
    fun providesSetNstackLanguageInteractorImpl(): SetNstackLanguageInteractor {
        return SetNstackLanguageInteractorImpl()
    }

    @Provides
    fun providesNewUseFirebaseAnalyticsInteractorImpl(context: Context): NewUseFirebaseAnalyticsInteractor {
        return NewUseFirebaseAnalyticsInteractorImpl(context)
    }

    @Provides
    fun providesGetGoogleMapNearbyPlacesInteractorImpl(googleApi: GoogleApi): GetGoogleMapNearbyPlacesInteractor {
        return GetGoogleMapNearbyPlacesInteractorImpl(googleApi)
    }

}