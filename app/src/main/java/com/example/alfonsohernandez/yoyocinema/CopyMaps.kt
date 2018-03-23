/*

/**
 * Created by alfonsohernandez on 22/03/2018.
 */
package com.example.alfonsohernandez.yoyocinema


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.location.places.*
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.OnSuccessListener

/**
 * A simple [Fragment] subclass.
 */
class Tab1Fragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private val TAG = "Tab1Fragment"

    private var mMap: GoogleMap? = null

    var mMapView: MapView? = null
    var googleMap: GoogleMap? = null

    var PROXIMITY_RADIUS = 10000
    val REQUEST_LOCATION_CODE = 99


    var latitude = 0.0
    var longitude = 0.0

    lateinit var client: GoogleApiClient

    private var locationRequest: LocationRequest? = null
    private var lastlocation: Location? = null
    private var currentLocationmMarker: Marker? = null

    lateinit var mPlaceDetectionClient: PlaceDetectionClient
    lateinit var mGoogleApiClient: GoogleApiClient

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab1, container, false)
        Log.d(TAG,"Test")

        mPlaceDetectionClient = Places.getPlaceDetectionClient(context!!)

        mMapView = rootView.findViewById(R.id.mapView)
        mMapView!!.onCreate(savedInstanceState)

        mMapView!!.onResume() // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView!!.getMapAsync { mMap ->

            googleMap = mMap
            googleMap!!.isMyLocationEnabled = true

        }

        return rootView
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGoogleApiClient = GoogleApiClient
                .Builder(activity!!)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(activity!!, this)
                .build()

        val placeFilter = PlaceFilter(false, listOf("movie_theater"))

        mPlaceDetectionClient
                .getCurrentPlace(placeFilter)
                .addOnFailureListener {
                    it.printStackTrace()
                }
                .addOnSuccessListener { results ->
                    results.forEach {
                        Log.d(TAG, "Place Result" + it)
                    }
                }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            bulidGoogleApiClient()
                        }
                        mMap!!.setMyLocationEnabled(true)
                    }
                } else {
                    Toast.makeText(context!!, "Permission Denied", Toast.LENGTH_LONG).show()
                }
        }
    }


    override fun onMapReady(p0: GoogleMap?) {

        Log.d(TAG,"onMapReady")

        mMap = googleMap

        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient()

            mMap!!.setMyLocationEnabled(true)

            //mMap?.moveCamera(CameraUpdateFactory.newLatLng())
        }

    }

    @Synchronized protected fun bulidGoogleApiClient() {
        client = GoogleApiClient.Builder(context!!).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        client.connect()
    }

    override fun onLocationChanged(location: Location) {

        latitude = location.getLatitude()
        longitude = location.getLongitude()
        lastlocation = location
        if (currentLocationmMarker != null) {
            currentLocationmMarker!!.remove()

        }
        Log.d(TAG, "" + latitude)
        val latLng = LatLng(location.getLatitude(), location.getLongitude())
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        currentLocationmMarker = googleMap!!.addMarker(markerOptions)
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap!!.animateCamera(CameraUpdateFactory.zoomBy(10f))

        if (client != null) {
            if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //LocationServices.FusedLocationApi.removeLocationUpdates(client, this)LocationServices.FusedLocationApi.removeLocationUpdates(client, this)
            var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
            mFusedLocationClient.lastLocation.addOnSuccessListener(activity!!, object : OnSuccessListener<Location> {
                override fun onSuccess(p0: Location?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }


    @SuppressLint("RestrictedApi")
    override fun onConnected(bundle: Bundle?) {

        locationRequest = LocationRequest()
        locationRequest!!.setInterval(100);
        locationRequest!!.setFastestInterval(1000);
        locationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this)
            var mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
            mFusedLocationClient.lastLocation.addOnSuccessListener(activity!!, object : OnSuccessListener<Location> {
                override fun onSuccess(p0: Location?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }

    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}
*/