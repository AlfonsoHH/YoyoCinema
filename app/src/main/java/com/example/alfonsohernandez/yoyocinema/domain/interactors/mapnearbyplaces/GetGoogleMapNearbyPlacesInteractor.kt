package com.example.alfonsohernandez.yoyocinema.domain.interactors.mapnearbyplaces

import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import com.example.alfonsohernandez.yoyocinema.domain.models.ResultsItem
import io.reactivex.Single

interface GetGoogleMapNearbyPlacesInteractor {
    fun getNearbyPlaces(type: String, location: String, radius: Int): Single<Response>
}