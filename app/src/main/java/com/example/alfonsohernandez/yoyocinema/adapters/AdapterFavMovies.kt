package com.example.alfonsohernandez.yoyocinema.adapters

import android.app.Activity
import android.content.Intent
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
import com.example.alfonsohernandez.yoyocinema.MovieDetailActivity
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.POJOMovie.MovieFav
import com.example.alfonsohernandez.yoyocinema.POJOMovie.ResultsItem
import jp.wasabeef.blurry.Blurry

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class AdapterFavMovies(val movieList: List<MovieFav?>?, val activity: Activity): RecyclerView.Adapter<AdapterFavMovies.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movieList!!.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_movies, parent, false)
        return ViewHolder(v, activity)
    }

    override fun getItemCount(): Int {
        return movieList!!.size
    }

    class ViewHolder(itemView: View, val activity: Activity): RecyclerView.ViewHolder(itemView){


        fun bindItems(movie: MovieFav?){

            val txtName = itemView.findViewById<TextView>(R.id.txtTitleRV)
            val txtTitle = itemView.findViewById<TextView>(R.id.txtDescriptionRV)
            val foto = itemView.findViewById<ImageView>(R.id.photoMovieRV)
            val fotoBg = itemView.findViewById<ImageView>(R.id.photoMovieBgRV)

            txtName?.text = movie?.title
            txtTitle?.text = movie?.overview

            Glide.with(activity).asBitmap().load("http://image.tmdb.org/t/p/w185/"+movie?.posterPath).into(object : SimpleTarget<Bitmap>(400,400) {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Blurry.with(activity).radius(80).from(resource).into(fotoBg)
                }
            })

            Glide.with(activity).load("http://image.tmdb.org/t/p/w185/"+movie?.posterPath).into(foto)

            itemView.setOnClickListener({
                var intent: Intent = Intent(activity, MovieDetailActivity::class.java)
                val moviConversion = ResultsItem(movie?.overview,null,null,null,movie?.title,null,movie?.posterPath,null,movie?.releaseDate,movie?.voteAverage,null,null,null,null)
                intent.putExtra("movie",moviConversion)
                activity.startActivity(intent)

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