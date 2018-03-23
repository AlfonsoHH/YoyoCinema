package com.example.alfonsohernandez.yoyocinema.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.alfonsohernandez.yoyocinema.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

/**
 * Created by alfonsohernandez on 23/03/2018.
 */
class CustomInfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(p0: Marker?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.cinema_information,null)



        //p0.

        return view
    }

    override fun getInfoWindow(p0: Marker?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.cinema_information,null)

        var tvName = view.findViewById<TextView>(R.id.tvCinemaName)
        var tvAddress = view.findViewById<TextView>(R.id.tvCinemaDirection)
        var ivPhoto = view.findViewById<ImageView>(R.id.ivCinemaPhoto)

        tvName.text = p0?.title
        tvAddress.text = p0?.snippet

        return view

    }
}