package com.example.alfonsohernandez.yoyocinema.domain.injection.components

import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.injection.scopes.ActivityScope
import com.example.alfonsohernandez.yoyocinema.presentation.discover.DiscoverFragment
import com.example.alfonsohernandez.yoyocinema.presentation.favorites.FavoritesFragment
import com.example.alfonsohernandez.yoyocinema.presentation.login.LoginActivity
import com.example.alfonsohernandez.yoyocinema.presentation.map.MapFragment
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailActivity
import com.example.alfonsohernandez.yoyocinema.presentation.profile.ProfileFragment
import com.example.alfonsohernandez.yoyocinema.presentation.tabs.TabActivity
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(PresentationModule::class))
@ActivityScope
interface PresentationComponent {
    fun inject(target: DiscoverFragment)
    fun inject(target: FavoritesFragment)
    fun inject(target: MovieDetailActivity)
    fun inject(target: ProfileFragment)
    fun inject(target: LoginActivity)
    fun inject(target: TabActivity)
    fun inject(target: MapFragment)
}