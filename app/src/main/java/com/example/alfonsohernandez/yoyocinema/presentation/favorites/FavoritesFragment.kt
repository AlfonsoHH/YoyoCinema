package com.example.alfonsohernandez.yoyocinema.presentation.favorites

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailActivity
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterMovies
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment : Fragment(), SearchView.OnQueryTextListener, FavoritesContract.View {

    @Inject
    lateinit var presenter: FavoritesPresenter

    var favoritesList: List<MovieResultsItem> = mutableListOf()

    var adapter = AdapterMovies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onDestroy() {
        presenter.setView(null)
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectDependencies()

        presenter.setView(this)
        if(isNetworkAvailable()){
            presenter.loadFirebaseData() as? List<MovieResultsItem>
        }else{
            presenter.loadCache()
        }
        setupRecycler()

        searchMovieFav.setOnQueryTextListener(this)
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    fun setupRecycler() {
        rvMoviesFavFragment.layoutManager = LinearLayoutManager(context)
        rvMoviesFavFragment.adapter = adapter

        adapter.onMovieClickedListener = { movie ->
            val intent: Intent = Intent(context, MovieDetailActivity::class.java)

            presenter.updateCache("movie-"+movie.id.toString(), listOf(movie))

            intent.putExtra("movie", movie.id.toString())
            startActivity(intent)
        }
    }

    override fun setData(data: List<MovieResultsItem>) {
        adapter.movieList.clear()
        adapter.movieList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showProgress(isLoading: Boolean) {
        println("Showing progress")
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        queryMethods(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        queryMethods(newText)
        return true
    }

    fun queryMethods(query: String) {
        if(favoritesList!!.isNotEmpty()){
            if(!searchMovieFav.query.equals("")) {
                if(isNetworkAvailable()){
                    presenter.loadSearchData(query)
                }else{
                    presenter.loadSearchDataOffline(query)
                }
            }else{
                if(isNetworkAvailable()) {
                    presenter.loadFirebaseData()
                }else{
                    presenter.loadCache()
                }
            }
        }
    }

    override fun showError() {
        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}