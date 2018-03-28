package com.example.alfonsohernandez.yoyocinema.presentation.discover

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterMovies
import android.support.v4.widget.SwipeRefreshLayout
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_discover.*
import javax.inject.Inject


@Suppress("UNREACHABLE_CODE")
/**
 * A simple [Fragment] subclass.
 */
class DiscoverFragment : Fragment(), DiscoverContract.View, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var presenter: DiscoverPresenter

    var movieList: List<MovieResultsItem?>? = mutableListOf()
    var adapter = AdapterMovies()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
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
            presenter.loadDiscoverData()
        }else{
            presenter.loadCache("discover")
        }
        setupRecycler()

        swipeContainer.setOnRefreshListener(this)
        searchMovie.setOnQueryTextListener(this)
    }

    fun injectDependencies() {
        App.instance.component.plus(PresentationModule()).inject(this)
    }

    fun setupRecycler() {
        rvMoviesFragment.layoutManager = LinearLayoutManager(context)
        rvMoviesFragment.adapter = adapter

        adapter.onMovieClickedListener = { movie ->
            val intent: Intent = Intent(context, MovieDetailActivity::class.java)

            presenter.updateCache("movie-"+movie.id.toString(), listOf(movie))

            intent.putExtra("movie", movie.id.toString())
            startActivity(intent)
        }
    }

    //Method that tell the adapter what to do when the data change
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
        if (!searchMovie.query.isEmpty()) {
            if (isNetworkAvailable()) {
                presenter.loadSearchData(query)
            } else {
                presenter.loadCache(searchMovie.query.toString()) as? List<MovieResultsItem?>
                movieList?.filter { it?.title == searchMovie.query.toString() }
            }
        } else {
            if (isNetworkAvailable()) {
                presenter.loadDiscoverData()
            } else {
                presenter.loadSearchDataOffline("discover")
            }
        }
    }

    override fun onRefresh() {
        if (!searchMovie.query.isEmpty()) {
            presenter.loadSearchData(searchMovie.query.toString())
        } else {
            presenter.loadDiscoverData()
        }
        swipeContainer.isRefreshing = false
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


