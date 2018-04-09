package com.example.alfonsohernandez.yoyocinema.presentation.discover

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alfonsohernandez.yoyocinema.presentation.adapters.AdapterMovies
import android.support.v4.widget.SwipeRefreshLayout
import android.net.ConnectivityManager
import android.util.Log
import com.example.alfonsohernandez.yoyocinema.App
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.domain.injection.modules.PresentationModule
import com.example.alfonsohernandez.yoyocinema.presentation.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_discover.*
import javax.inject.Inject
import android.widget.*
import com.example.alfonsohernandez.yoyocinema.domain.setVisibility


open class DiscoverFragment : Fragment(), DiscoverContract.View, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var presenter: DiscoverPresenter

    var adapter = AdapterMovies()

    private val TAG = "DiscoverFragment"

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
            val intent = Intent(context, MovieDetailActivity::class.java)

            presenter.updateCache("movie-" + movie.id.toString(), listOf(movie))

            intent.putExtra("movie", movie.id.toString())
            startActivity(intent)
        }
    }

    override fun setData(data: List<MovieResultsItem>?) {
        adapter.movieList.clear()
        adapter.movieList.addAll(data!!)
        adapter.notifyDataSetChanged()
    }


    override fun showProgress(isLoading: Boolean) {
        progressBarDiscover.setVisibility(isLoading)
        rvMoviesFragment.setVisibility(!isLoading)
        if (!isLoading) {
            swipeContainer.isRefreshing = false
        }
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
            presenter.loadCache("search-" + query)
            presenter.loadSearchDataRxJava(query)
        } else {
            presenter.loadCache("discover")
            presenter.loadDiscoverDataRxJava()
        }
    }

    override fun onRefresh() {
        queryMethods(searchMovie.query.toString())
        swipeContainer.isRefreshing = false
    }

    override fun showError() {
        Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
    }

}


