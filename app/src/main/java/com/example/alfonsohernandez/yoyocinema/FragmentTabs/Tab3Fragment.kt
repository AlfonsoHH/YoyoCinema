package com.example.alfonsohernandez.yoyocinema.FragmentTabs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.alfonsohernandez.yoyocinema.adapters.AdapterFavMovies
import com.google.firebase.database.*
import android.widget.SearchView
import com.example.alfonsohernandez.yoyocinema.POJOMovie.MovieFav
import com.example.alfonsohernandez.yoyocinema.R
import io.paperdb.Paper


/**
 * A simple [Fragment] subclass.
 */
class Tab3Fragment : Fragment() {

    lateinit var ref: DatabaseReference

    lateinit var movieFav: MovieFav
    lateinit var favoritesList: MutableList<MovieFav>
    lateinit var favoritesListSearch: MutableList<MovieFav>

    lateinit var rv: RecyclerView
    lateinit var sv: SearchView

    var pref: SharedPreferences? = null

    var online: Boolean = true


    lateinit var activityHere: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_tab3, container, false)

        rv = rootView.findViewById<RecyclerView>(R.id.rvMoviesFavFragment)
        sv = rootView.findViewById<SearchView>(R.id.searchMovieFav)
        rv.layoutManager = LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false)

        pref = this.activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)

        activityHere = getActivity() as Activity

        if(isNetworkAvailable()) {
            ref = FirebaseDatabase.getInstance().getReference("MoviesFav")

            ref.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
                override fun onDataChange(p0: DataSnapshot?) {
                    if(p0!!.exists()){
                        favoritesList = mutableListOf()
                        println("Entra en Data Change")
                        for (h in p0.children){
                            var movieFavFB = h.getValue(MovieFav::class.java)
                            if(movieFavFB!!.user.equals(pref?.getString("firstName","jonhDoe"))) {
                                favoritesList.add(movieFavFB)
                            }
                        }

                        updateCache(pref!!.getString("firstName","jonhDoe"))

                        var adapter = AdapterFavMovies(favoritesList,activityHere)
                        rv.adapter = adapter

                    }else{
                        favoritesList = mutableListOf()
                    }
                }
            })
        }else{
            favoritesList = Paper.book().read(pref?.getString("firstName","jonhDoe"))
        }



        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(q: String): Boolean {
                if(favoritesList.isNotEmpty()){
                    if(!sv.query.equals("")) {
                        favoritesListSearch = mutableListOf()

                        for (movieFavNow in favoritesList) {
                            if (movieFavNow.title!!.contains(sv.query)) {
                                favoritesListSearch.add(movieFavNow)
                            }
                        }
                        var adapter = AdapterFavMovies(favoritesListSearch,activityHere)
                        rv.adapter = adapter

                    }else{
                        var adapter = AdapterFavMovies(favoritesList,activityHere)
                        rv.adapter = adapter
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(q: String): Boolean {
                return false
            }
        })

        return rootView

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activityHere.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun loadCache(clave: String){

        favoritesList = Paper.book().read("user-"+clave)

        if(favoritesList.isNotEmpty()){
            var adapter = AdapterFavMovies(favoritesList, activityHere)
            rv.adapter = adapter
        }

    }

    fun updateCache(clave: String){

        Paper.book().delete("user-"+clave)
        for (movieNow in favoritesList){
            Paper.book().write("user-"+clave,movieNow)
        }

    }

}// Required empty public constructor