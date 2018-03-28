package com.example.alfonsohernandez.yoyocinema.presentation.map

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.domain.models.Cinema
import com.example.alfonsohernandez.yoyocinema.domain.models.MarkerGM
import com.example.alfonsohernandez.yoyocinema.domain.models.Response
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.places.*
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApiService
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import retrofit2.Call
import retrofit2.Callback


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, ConnectionCallbacks, GoogleMap.OnMarkerClickListener {


    private val TAG = "MapFragment"

    lateinit var mMapView: MapView
    private var mMap: GoogleMap? = null

    lateinit var mPlaceDetectionClient: PlaceDetectionClient
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var client: GoogleApiClient

    private var apiService = GoogleApiService()

    var latLong: LatLng? = null

    var markers: ArrayList<MarkerGM> = arrayListOf()

    lateinit var mClusterManager: ClusterManager<Cinema>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        Log.d(TAG, "Test")

        mMapView = rootView.findViewById(R.id.mapView)

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume() // needed to get the map to display immediately



        mMapView.getMapAsync(this)

        mPlaceDetectionClient = Places.getPlaceDetectionClient(context!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        return rootView
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated")

        /*mGoogleApiClient = GoogleApiClient
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
                    Log.d(TAG, "Dentro de onFailure")
                }
                .addOnSuccessListener { results ->
                    results.forEach {
                        Log.d(TAG, "Place Result" + it)
                    }
                }
    */
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {

        Log.d(TAG, "onMapReady")

        mMap = p0

        mMap!!.setBuildingsEnabled(true)
        mMap!!.setIndoorEnabled(true)
        mMap!!.setTrafficEnabled(true)

        var mUiSettings = mMap!!.getUiSettings()
        mUiSettings.setZoomControlsEnabled(true)
        mUiSettings.setCompassEnabled(true)
        mUiSettings.setMyLocationButtonEnabled(true)
        mUiSettings.setScrollGesturesEnabled(true)
        mUiSettings.setZoomGesturesEnabled(true)
        mUiSettings.setTiltGesturesEnabled(true)
        mUiSettings.setRotateGesturesEnabled(true)

        mMap!!.setMyLocationEnabled(true)

        mMap!!.setOnMarkerClickListener(this)

        bulidGoogleApiClient()


        /*if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient()

            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                        latLong = LatLng(location!!.latitude, location!!.longitude)

                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12f))

                        latLong?.let {
                            findNearbyPlaces("movie_theater", it)
                        }

                    }

        }*/

    }

    @SuppressLint("MissingPermission")
    fun showCurrentPlace() {

        var placeResult: Task<PlaceLikelihoodBufferResponse> = mPlaceDetectionClient.getCurrentPlace(null)
        placeResult.addOnCompleteListener(object : OnCompleteListener<PlaceLikelihoodBufferResponse> {
            @SuppressLint("RestrictedApi")
            override fun onComplete(task: Task<PlaceLikelihoodBufferResponse>) {

                if (task.isSuccessful() && task.getResult() != null) {

                    var likelyPlaces = task.getResult()

                    // Set the count, handling cases where less than 5 entries are returned.
                    var count = 0
                    if (likelyPlaces.count < 10) {
                        count = likelyPlaces.count
                    } else {
                        count = 10
                    }

                    var i = 0

                    val mLikelyPlaceNames = arrayOfNulls<String>(count)
                    val mLikelyPlaceAddresses = arrayOfNulls<String>(count)
                    val mLikelyPlaceAttributions = arrayOfNulls<String>(count)

                    val mLikelyPlaceLatLngs = arrayOfNulls<LatLng>(count)


                    for (placeLikelihood in likelyPlaces) {
                        // Build a list of likely places to show the user.
                        mLikelyPlaceNames[i] = placeLikelihood.place.name.toString()
                        mLikelyPlaceAddresses[i] = placeLikelihood.place.address.toString()
                        //mLikelyPlaceAttributions[i] = placeLikelihood.place.attributions.toString()
                        mLikelyPlaceLatLngs[i] = placeLikelihood.place.latLng

                        mMap!!.addMarker((MarkerOptions().position(placeLikelihood.place.latLng).title(placeLikelihood.place.name.toString())))

                        i++;
                        if (i > (count - 1)) {
                            break;
                        }
                    }

                    // Release the place likelihood buffer, to avoid memory leaks.
                    likelyPlaces.release();

                    // Show a dialog offering the user the list of likely places, and add a
                    // marker at the selected place.
                    //openPlacesDialog();

                } else {
                    Log.e(TAG, "Exception: %s", task.getException());
                }

            }
        })

    }

    fun findNearbyPlaces(type: String, latLng: LatLng) {

        val service = apiService.createService()

        val call = service.getNearbyPlaces(type, latLng.latitude.toString() + "," + latLng.longitude.toString(), 10000)

        call.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {

                try {
                    mMap!!.clear()

                    mClusterManager = ClusterManager<Cinema>(context!!, mMap)

                    //mMap!!.setInfoWindowAdapter(CustomInfoWindowAdapter(context!!))

                    mMap!!.setOnCameraIdleListener(mClusterManager)
                    mMap!!.setOnMarkerClickListener(mClusterManager)
                    mMap!!.setOnInfoWindowClickListener(mClusterManager)

                    //var renderer = RenderClusterInfoWindow(context, mMap, mClusterManager)
                    //mClusterManager.setRenderer(renderer)

                    // This loop will go through all the results and add marker on each location.
                    for (i in 0 until response?.body()?.results!!.size) {

                        val lat = response.body()?.results!!.get(i)?.geometry!!.location!!.lat
                        val lng = response.body()?.results!!.get(i)?.geometry!!.location!!.lng
                        val placeName = response.body()?.results!!.get(i)?.name
                        val vicinity = response.body()?.results!!.get(i)?.vicinity
                        val markerOptions = MarkerOptions()
                        val latLngNow = LatLng(lat!!, lng!!)

                        mClusterManager.addItem(Cinema(lat, lng, placeName, vicinity!!))

                        // Position of Marker on Map
                        markerOptions.position(latLngNow)

                        // Adding Title to the Marker
                        markerOptions.title(placeName + " : " + vicinity)

                        // Adding Marker to the Camera.
                        //val m = mMap!!.addMarker(markerOptions)
                        var markerNow: MarkerGM = MarkerGM(placeName + " : " + vicinity, response.body()?.results!!.get(i)?.reference, response.body()?.results!!.get(i)?.types, response.body()?.results!!.get(i)?.scope, response.body()?.results!!.get(i)?.icon, response.body()?.results!!.get(i)?.name, response.body()?.results!!.get(i)?.openingHours, response.body()?.results!!.get(i)?.rating, response.body()?.results!!.get(i)?.geometry, response.body()?.results!!.get(i)?.vicinity, response.body()?.results!!.get(i)?.id, response.body()?.results!!.get(i)?.photos, response.body()?.results!!.get(i)?.placeId)
                        markers.add(markerNow)

                        // Adding colour to the marker
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                    }

                    mClusterManager.setOnClusterClickListener(object : ClusterManager.OnClusterClickListener<Cinema>{
                        override fun onClusterClick(p0: Cluster<Cinema>?): Boolean {
                            // custom dialog
                            var dialog = Dialog(context);
                            dialog.setContentView(R.layout.cinema_information);
                            dialog.setTitle("Movie Theater");

                            // set the custom dialog components - text, image and button
                            var name = dialog.findViewById<TextView>(R.id.tvCinemaName)

                            var texto: String? = ""

                            for (item in p0!!.items){
                                texto = texto + item.name+ "\n"
                            }

                            name.text = texto

                            var dialogButton = dialog.findViewById<Button>(R.id.btCinemaDialog)
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    dialog.dismiss()
                                }
                            })

                            dialog.show()

                            return true
                        }
                    })

                    mClusterManager.setOnClusterItemClickListener(object : ClusterManager.OnClusterItemClickListener<Cinema>{
                        override fun onClusterItemClick(p0: Cinema?): Boolean {
                            Log.d(TAG,p0!!.name)

                            // custom dialog
                            var dialog = Dialog(context);
                            dialog.setContentView(R.layout.cinema_information);
                            dialog.setTitle("Movie Theater");

                            // set the custom dialog components - text, image and button
                            var name = dialog.findViewById<TextView>(R.id.tvCinemaName)
                            var address = dialog.findViewById<TextView>(R.id.tvCinemaDirection)
                            var photo = dialog.findViewById<ImageView>(R.id.ivCinemaPhoto)

                            for (markerNow in markers) {
                                if (p0!!.name.equals(markerNow.name)) {


                                    name.text = markerNow.name
                                    address.text = markerNow.vicinity

                                    markerNow.photos?.let {
                                        Glide.with(activity!!).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=" + it[0]!!.photoReference + "&key=AIzaSyDwSirgtRvVHy32qzbSfHmeK8Oh21iij2Q").into(photo)
                                    }
                                }
                            }

                            var dialogButton = dialog.findViewById<Button>(R.id.btCinemaDialog)
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    dialog.dismiss()
                                }
                            })

                            dialog.show()

                            return true
                        }
                    })

                    mClusterManager.cluster()
                } catch (e: Exception) {
                    Log.d("onResponse", "There is an error")
                    e.printStackTrace()
                }


            }
        })
    }


    @Synchronized protected fun bulidGoogleApiClient() {
        client = GoogleApiClient.Builder(context!!).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        client.connect()
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    @SuppressLint("RestrictedApi")
    override fun onConnected(p0: Bundle?) {
        var mLocationRequest = LocationRequest()
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                        var latLong = LatLng(location!!.latitude, location!!.longitude)

                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12f))

                        latLong?.let{
                            findNearbyPlaces("movie_theater", it)
                        }

                    }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        Log.d(TAG,"Inside Marker Click")

        // custom dialog
        var dialog = Dialog(context);
        dialog.setContentView(R.layout.cinema_information);
        dialog.setTitle("Movie Theater");

			// set the custom dialog components - text, image and button
			var name = dialog.findViewById<TextView>(R.id.tvCinemaName)
            var address = dialog.findViewById<TextView>(R.id.tvCinemaDirection)
            var photo = dialog.findViewById<ImageView>(R.id.ivCinemaPhoto)
            var position = marker!!.getTag()

            for (markerNow in markers){
                if(marker.title.equals(markerNow.title)){


                    name.text=markerNow.name
                    address.text=markerNow.vicinity

                    markerNow.photos?.let{
                        Glide.with(this).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=" + it[0]!!.photoReference + "&key=AIzaSyDwSirgtRvVHy32qzbSfHmeK8Oh21iij2Q").into(photo)
                    }
                }
            }

			var dialogButton = dialog.findViewById<Button>(R.id.btCinemaDialog)
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    dialog.dismiss()
                }
            })

        dialog.show()

        return true
    }

    override fun onConnectionSuspended(p0: Int) {
    }

}