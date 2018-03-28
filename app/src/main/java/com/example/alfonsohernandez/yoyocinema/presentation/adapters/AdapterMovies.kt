package com.example.alfonsohernandez.yoyocinema.presentation.adapters

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.alfonsohernandez.yoyocinema.domain.models.MovieResultsItem
import com.example.alfonsohernandez.yoyocinema.R
import jp.wasabeef.blurry.Blurry

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class AdapterMovies : RecyclerView.Adapter<AdapterMovies.ViewHolder>() {
    var movieList: ArrayList<MovieResultsItem> = arrayListOf()

    var onMovieClickedListener: ((MovieResultsItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movieList!!.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_movies, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindItems(movie: MovieResultsItem) {

            val txtName = itemView.findViewById<TextView>(R.id.txtTitleRV)
            val txtTitle = itemView.findViewById<TextView>(R.id.txtDescriptionRV)
            val foto = itemView.findViewById<ImageView>(R.id.photoMovieRV)
            val fotoBg = itemView.findViewById<ImageView>(R.id.photoMovieBgRV)

            txtName?.text = movie.title
            txtTitle?.text = movie.overview

            Glide.with(itemView).asBitmap().load("http://image.tmdb.org/t/p/w185/" + movie?.posterPath).into(object : SimpleTarget<Bitmap>(400, 400) {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Blurry.with(itemView.context).radius(80).from(resource).into(fotoBg)
                }
            })

            Glide.with(itemView).load("http://image.tmdb.org/t/p/w185/" + movie?.posterPath).into(foto)

            itemView.setOnClickListener({
                onMovieClickedListener?.invoke(movie)
            })

            /*itemView.setOnLongClickListener({
                val alertDilog = AlertDialog.Builder(activity).create()
                alertDilog.setTitle("Alert")
                alertDilog.setMessage("Do you want to add this movie to Favorites?")

                alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", {
                    dialogInterface, i ->  //ref.child(reto.idReto).removeValue().addOnCompleteListener{
                }

                })

                alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", {
                    //dialogInterface, i ->
                })
                alertDilog.show()
                return@setOnLongClickListener true
            })
*/

        }
    }

}