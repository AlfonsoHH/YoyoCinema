package com.example.alfonsohernandez.yoyocinema

import android.app.Activity
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
import com.example.alfonsohernandez.yoyocinema.models.ResultsItem
import com.example.alfonsohernandez.yoyocinema.models.User
import com.example.alfonsohernandez.yoyocinema.rest.ApiService
import kotlinx.android.synthetic.main.fragment_tab2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNREACHABLE_CODE")
/**
 * A simple [Fragment] subclass.
 */
class Tab2Fragment : Fragment() {

    private var apiService = ApiService()

    var movieList: List<ResultsItem?>? = mutableListOf()
    lateinit var actualUser: User
    lateinit var rv: RecyclerView
    lateinit var activity: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_tab2, container, false)

        println("Aqui estamos")

        activity = getActivity() as Activity

        rv = rootView.findViewById<RecyclerView>(R.id.rvMoviesFragment)
        rv.layoutManager = LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false)

        //actualUser = intent.getSerializableExtra("user") as User
        actualUser = User("Alfon", "Weeee", "mail@mail.com", "url")

        /*movieList = mutableListOf()

        val discoverMovies = apiService.createService().getSearchedMovie("c9221bf28759d13b63e8730e5af4a329",query)
        discoverMovies.enqueue(object : Callback<com.example.alfonsohernandez.yoyocinema.models.Response> {
            override fun onFailure(call: Call<com.example.alfonsohernandez.yoyocinema.models.Response>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<com.example.alfonsohernandez.yoyocinema.models.Response>?, response: Response<com.example.alfonsohernandez.yoyocinema.models.Response>?) {
                println(response?.body())
                var consulta = response?.body()

                movieList = consulta!!.results

                var adapter = AdapterListDiscoveries(movieList, activity, actualUser)
                rv.adapter = adapter

            }
        })*/

        /*var movieCall = apiService.createService().getMovie("550", "c9221bf28759d13b63e8730e5af4a329")
        movieCall.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                println(response?.body())
                movieList.add(0,response?.body())

                println(movieList.get(0)?.title)

                var adapter = AdapterListDiscoveries(movieList, activity, actualUser)
                rv?.adapter = adapter

            }
        })*/

        var discoverMovies = apiService.createService().getDiscoveryMovies("c9221bf28759d13b63e8730e5af4a329")
        discoverMovies.enqueue(object : Callback<com.example.alfonsohernandez.yoyocinema.models.Response> {
            override fun onFailure(call: Call<com.example.alfonsohernandez.yoyocinema.models.Response>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<com.example.alfonsohernandez.yoyocinema.models.Response>?, response: Response<com.example.alfonsohernandez.yoyocinema.models.Response>?) {
                println(response?.body())
                var consulta = response?.body()

                movieList = consulta!!.results

                var adapter = AdapterListDiscoveries(movieList, activity, actualUser)
                rv.adapter = adapter

            }
        })


        // Inflate the layout for this fragment
        return rootView

    }


}// Required empty public constructor

