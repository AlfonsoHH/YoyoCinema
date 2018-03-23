package com.example.alfonsohernandez.yoyocinema.POJOGoogle

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem



/**
 * Created by alfonsohernandez on 22/03/2018.
 */
class Cinema(lat: Double, lng: Double, var name: String?, private val vicinity: String) : ClusterItem {

    private val mPosition: LatLng

    init {
        mPosition = LatLng(lat, lng)
    }

    override fun getPosition(): LatLng {
        return mPosition
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String {
        return vicinity
    }
}