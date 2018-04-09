package com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces

import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApi
import io.reactivex.Single

open class GetGoogleMapNearbyPlacesInteractorImpl(private val googleApi: GoogleApi) : GetGoogleMapNearbyPlacesInteractor {
    override fun getNearbyPlaces(type: String, location: String, radius: Int): Single<Response> {
        return googleApi.getNearbyPlaces(type, location, radius)
    }
}