package com.example.alfonsohernandez.yoyocinema.domain.injection.modules

import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.ActivityScope
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.GetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.cache.SetCacheInfoInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.discovermovies.GetDiscoverMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebaseanalytics.NewUseFirebaseAnalyticsInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.AddFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.GetFavoriteMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.firebasefavorites.RemoveFavoriteMovieInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces.GetGoogleMapNearbyPlacesInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.GetNStackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.nstack.SetNstackLanguageInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.GetUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.preference.SaveUserProfileInteractor
import com.example.alfonsohernandez.yoyocinema.domain.interactors.searchmovies.GetSearchMoviesInteractor
import com.example.alfonsohernandez.yoyocinema.presentation.discover.DiscoverContract
import com.example.alfonsohernandez.yoyocinema.presentation.discover.DiscoverPresenter
import com.example.alfonsohernandez.yoyocinema.presentation.favorites.FavoritesPresenter
import com.example.alfonsohernandez.yoyocinema.presentation.favorites.FavoritesContract
import com.example.alfonsohernandez.yoyocinema.presentation.login.LoginContract
import com.example.alfonsohernandez.yoyocinema.presentation.login.LoginPresenter
import com.example.alfonsohernandez.yoyocinema.presentation.map.MapContract
import com.example.alfonsohernandez.yoyocinema.presentation.map.MapPresenter
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailContract
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailPresenter
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfileContract
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfilePresenter
import com.example.alfonsohernandez.yoyocinema.presentation.tabs.TabContract
import com.example.alfonsohernandez.yoyocinema.presentation.tabs.TabPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    @ActivityScope
    fun providesDiscoverPresenter(getCacheInfoInteractor: GetCacheInfoInteractor, setCacheInfoInteractor: SetCacheInfoInteractor, getDiscoverMoviesInteractor: GetDiscoverMoviesInteractor, getSearchMoviesInteractor: GetSearchMoviesInteractor): DiscoverContract.Presenter {
        return DiscoverPresenter(getCacheInfoInteractor, setCacheInfoInteractor, getDiscoverMoviesInteractor, getSearchMoviesInteractor)
    }

    @Provides
    @ActivityScope
    fun providesFavoritePresenter(getCacheInfoInteractor: GetCacheInfoInteractor, setCacheInfoInteractor: SetCacheInfoInteractor, getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor, GetUserProfileInteractor: GetUserProfileInteractor): FavoritesContract.Presenter {
        return FavoritesPresenter(getCacheInfoInteractor, setCacheInfoInteractor, getFavoriteMoviesInteractor, GetUserProfileInteractor)
    }

    @Provides
    @ActivityScope
    fun providesMovieDetailPresenter(getCacheInfoInteractor: GetCacheInfoInteractor, addFavoriteMoviesInteractor: AddFavoriteMovieInteractor, removeFavoriteMovieInteractor: RemoveFavoriteMovieInteractor, getFavoriteMoviesInteractor: GetFavoriteMoviesInteractor, getUserProfileInteractor: GetUserProfileInteractor): MovieDetailContract.Presenter {
        return MovieDetailPresenter(getCacheInfoInteractor, addFavoriteMoviesInteractor, removeFavoriteMovieInteractor, getFavoriteMoviesInteractor, getUserProfileInteractor)
    }

    @Provides
    @ActivityScope
    fun providesProfilePresenter(getUserProfileInteractor: GetUserProfileInteractor, savePreferencesParameterInteractor: SaveUserProfileInteractor, setNStackLanguageInteractor: SetNstackLanguageInteractor): ProfileContract.Presenter {
        return ProfilePresenter(getUserProfileInteractor,savePreferencesParameterInteractor,setNStackLanguageInteractor)
    }

    @Provides
    @ActivityScope
    fun providesLoginPresenter(saveUserProfileInteractor: SaveUserProfileInteractor, NewUseFirebaseAnalyticsInteractor: NewUseFirebaseAnalyticsInteractor, setNStackLanguageInteractor: SetNstackLanguageInteractor): LoginContract.Presenter {
        return LoginPresenter(saveUserProfileInteractor, NewUseFirebaseAnalyticsInteractor,setNStackLanguageInteractor)
    }

    @Provides
    @ActivityScope
    fun providesTabPresenter(getUserProfileInteractor: GetUserProfileInteractor, NewUseFirebaseAnalyticsInteractor: NewUseFirebaseAnalyticsInteractor): TabContract.Presenter {
        return TabPresenter(getUserProfileInteractor, NewUseFirebaseAnalyticsInteractor)
    }

    @Provides
    @ActivityScope
    fun providesMapPresenter(getGoogleMapNearbyPlacesInteractor: GetGoogleMapNearbyPlacesInteractor): MapContract.Presenter {
        return MapPresenter(getGoogleMapNearbyPlacesInteractor)
    }

}