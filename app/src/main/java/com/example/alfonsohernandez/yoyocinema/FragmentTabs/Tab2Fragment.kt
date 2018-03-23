package com.example.alfonsohernandez.yoyocinema.FragmentTabs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import com.example.alfonsohernandez.yoyocinema.adapters.AdapterListDiscoveries
import com.example.alfonsohernandez.yoyocinema.rest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.widget.SwipeRefreshLayout
import io.paperdb.Paper
import android.net.ConnectivityManager
import com.example.alfonsohernandez.yoyocinema.POJOMovie.ResultsItem
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.POJOOthers.User


@Suppress("UNREACHABLE_CODE")
/**
 * A simple [Fragment] subclass.
 */
class Tab2Fragment : Fragment() {

    private var apiService = ApiService()

    var movieList: List<ResultsItem?>? = mutableListOf()
    var movieListSearch: List<ResultsItem?>? = mutableListOf()
    var movieListSearchOffline: MutableList<ResultsItem?>? = mutableListOf()
    lateinit var actualUser: User
    lateinit var rv: RecyclerView
    lateinit var sv: SearchView
    lateinit var activity: Activity
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab2, container, false)

        activity = getActivity() as Activity

        rv = rootView.findViewById<RecyclerView>(R.id.rvMoviesFragment)
        sv = rootView.findViewById<SearchView>(R.id.searchMovie)

        Paper.init(context)

        rv.layoutManager = LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false)

        val pref = this.activity.getSharedPreferences("pref", Context.MODE_PRIVATE)

        swipeContainer = rootView.findViewById<SwipeRefreshLayout>(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if(!sv.query.isEmpty()) {

                    loadSearchList()

                }else{

                    loadDiscoverList()

                }
            }
        })


        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(q: String): Boolean {
                if(!sv.query.isEmpty()) {

                    loadSearchList()

                }else{

                    loadDiscoverList()

                }
                return false
            }

            override fun onQueryTextSubmit(q: String): Boolean {
                return false
            }
        })

        loadDiscoverList()

        // Inflate the layout for this fragment
        return rootView

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun loadDiscoverList(){

        if(isNetworkAvailable()) {

            var discoverMovies = apiService.createService().getDiscoveryMovies("c9221bf28759d13b63e8730e5af4a329")
            discoverMovies.enqueue(object : Callback<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response> {
                override fun onFailure(call: Call<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?, response: Response<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?) {
                    println(response?.body())
                    var consulta = response?.body()

                    movieList = consulta!!.results

                    updateCache("discover")

                    var adapter = AdapterListDiscoveries(movieList, activity)
                    rv.adapter = adapter

                    swipeContainer.isRefreshing = false
                }

            })

        }else{
            loadCache("discover")
        }
    }

    fun loadSearchList(){
        if (isNetworkAvailable()){

            movieListSearch = mutableListOf()

            val searchMovies = apiService.createService().getSearchedMovie("c9221bf28759d13b63e8730e5af4a329",sv.query.toString())
            searchMovies.enqueue(object : Callback<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response> {
                override fun onFailure(call: Call<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?, response: Response<com.example.alfonsohernandez.yoyocinema.POJOMovie.Response>?) {
                    println(response?.body())
                    var consulta = response?.body()

                    movieListSearch = consulta!!.results

                    updateCache(sv.query.toString())

                    var adapter = AdapterListDiscoveries(movieListSearch, activity)
                    rv.adapter = adapter

                    swipeContainer.isRefreshing = false

                }
            })

        }else{
            loadCache(sv.query.toString())
        }
    }

    fun loadCache(clave: String){

        movieList = Paper.book().read(clave)

        if(movieList!!.isNotEmpty()){
            var adapter = AdapterListDiscoveries(movieList, activity)
            rv.adapter = adapter
        }

        swipeContainer.isRefreshing = false

    }

    fun updateCache(clave: String){

        Paper.book().delete(clave)
        for (movieNow in movieList!!){
            Paper.book().write(clave,movieNow)

        }

    }

}// Required empty public constructor


