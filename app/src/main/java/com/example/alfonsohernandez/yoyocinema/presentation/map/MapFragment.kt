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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.App
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
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.ResultsItem
import com.example.alfonsohernandez.yoyocinema.domain.setVisibility
import com.example.alfonsohernandez.yoyocinema.network.rest.GoogleApi
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.fragment_map.*
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, ConnectionCallbacks, GoogleMap.OnMarkerClickListener, MapContract.View, ClusterManager.OnClusterClickListener<Cinema>, ClusterManager.OnClusterItemClickListener<Cinema> {

    private val TAG = "MapFragment"

    lateinit var mMapView: MapView
    private var mMap: GoogleMap? = null

    lateinit var mPlaceDetectionClient: PlaceDetectionClient
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var client: GoogleApiClient

    var markers: ArrayList<MarkerGM> = arrayListOf()
    lateinit var mClusterManager: ClusterManager<Cinema>

    @Inject
    lateinit var presenter: MapPresenter

    lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        mMapView = rootView.findViewById(R.id.mapView)

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume() // needed to get the map to display immediately

        mMapView.getMapAsync(this)

        return rootView
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPlaceDetectionClient = Places.getPlaceDetectionClient(context!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)

        injectDependencies()

        setupDialog()

        presenter.setView(this)
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    fun setupDialog(){
        dialog = Dialog(context)
        dialog.setContentView(R.layout.progress_dialog_layout)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {

        Log.d(TAG, "onMapReady")

        mMap = p0

        mMap!!.setBuildingsEnabled(true)
        mMap!!.setIndoorEnabled(true)
        mMap!!.setTrafficEnabled(true)

        val mUiSettings = mMap!!.getUiSettings()
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

    }

    @Synchronized protected fun bulidGoogleApiClient() {
        client = GoogleApiClient.Builder(context!!).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        client.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {}

    @SuppressLint("RestrictedApi")
    override fun onConnected(p0: Bundle?) {
        var mLocationRequest = LocationRequest()
        mLocationRequest.setInterval(1000)
        mLocationRequest.setFastestInterval(1000)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        if (ContextCompat.checkSelfPermission(activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                        val latLong = LatLng(location!!.latitude, location.longitude)

                        latLong?.let{
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 12f))

                            presenter.loadSearchData("movie_theater",
                                    it.latitude.toString() + "," + it.longitude.toString(),
                                    10000)
                        }
                    }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean { return true }

    override fun onConnectionSuspended(p0: Int) {}

    override fun setData(data: List<ResultsItem?>?) {

        mMap!!.clear()

        mClusterManager = ClusterManager<Cinema>(context!!, mMap)

        mMap!!.setOnCameraIdleListener(mClusterManager)
        mMap!!.setOnMarkerClickListener(mClusterManager)
        mMap!!.setOnInfoWindowClickListener(mClusterManager)

        for (i in 0 until data!!.size) {

            val lat = data.get(i)?.geometry!!.location!!.lat
            val lng = data.get(i)?.geometry!!.location!!.lng
            val latLngNow = LatLng(lat!!, lng!!)

            val placeName = data.get(i)?.name
            val vicinity = data.get(i)?.vicinity

            val markerOptions = MarkerOptions()

            mClusterManager.addItem(Cinema(lat, lng, placeName, vicinity!!))

            markerOptions.position(latLngNow)
            markerOptions.title(placeName + " : " + vicinity)
            val markerNow = MarkerGM(placeName + " : " + vicinity,
                    data.get(i)?.reference,
                    data.get(i)?.types,
                    data.get(i)?.scope,
                    data.get(i)?.icon,
                    data.get(i)?.name,
                    data.get(i)?.openingHours,
                    data.get(i)?.rating,
                    data.get(i)?.geometry,
                    data.get(i)?.vicinity,
                    data.get(i)?.id,
                    data.get(i)?.photos,
                    data.get(i)?.placeId)

            markers.add(markerNow)

            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

        }

        mClusterManager.cluster()

        mClusterManager.setOnClusterClickListener(this)
        mClusterManager.setOnClusterItemClickListener(this)
    }

    override fun onClusterClick(p0: Cluster<Cinema>?): Boolean {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.cinema_information)
        dialog.setTitle("Movie Theater")

        val name = dialog.findViewById<TextView>(R.id.tvCinemaName)
        val dialogButton = dialog.findViewById<TextView>(R.id.btCinemaDialog)

        var texto: String? = ""

        for (item in p0!!.items){
            texto = texto + item.name+ "\n"
        }

        name.text = texto

        dialogButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dialog.dismiss()
            }
        })

        dialog.show()

        return true
    }

    override fun onClusterItemClick(p0: Cinema?): Boolean {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.cinema_information)
        dialog.setTitle("Movie Theater")

        val name = dialog.findViewById<TextView>(R.id.tvCinemaName)
        val address = dialog.findViewById<TextView>(R.id.tvCinemaDirection)
        val photo = dialog.findViewById<ImageView>(R.id.ivCinemaPhoto)

        for (markerNow in markers) {
            if (p0!!.name.equals(markerNow.name)) {


                name.text = markerNow.name
                address.text = markerNow.vicinity

                markerNow.photos?.let {
                    Glide.with(activity!!).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=" + it[0]!!.photoReference + "&key=AIzaSyDwSirgtRvVHy32qzbSfHmeK8Oh21iij2Q").into(photo)
                }
            }
        }

        val dialogButton = dialog.findViewById<Button>(R.id.btCinemaDialog)
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dialog.dismiss()
            }
        })

        dialog.show()

        return true
    }

    override fun showProgress(isLoading: Boolean) {
        progressBarMaps.setVisibility(isLoading)
        mapView.setVisibility(!isLoading)
    }

    override fun showError() {
        Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
    }

}