package com.example.alfonsohernandez.yoyocinema.adapters

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.alfonsohernandez.yoyocinema.MovieDetailActivity
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.models.ResultsItem
import com.example.alfonsohernandez.yoyocinema.models.User

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class AdapterListDiscoveries(val movieList: List<ResultsItem?>?, val activity: Activity, var actualUser: User): RecyclerView.Adapter<AdapterListDiscoveries.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(movieList!!.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_movies, parent, false)
        return ViewHolder(v, activity, actualUser)
    }

    override fun getItemCount(): Int {
        return movieList!!.size
    }

    class ViewHolder(itemView: View, val activity: Activity, var actualUser: User): RecyclerView.ViewHolder(itemView){


        fun bindItems(movie: ResultsItem?){

            val txtName = itemView.findViewById<TextView>(R.id.txtTitleRV)
            val txtTitle = itemView.findViewById<TextView>(R.id.txtDescriptionRV)
            val foto = itemView.findViewById<ImageView>(R.id.photoMovieRV)

            println("Dentro del Adaptador")

            txtName?.text = movie?.title
            txtTitle?.text = movie?.overview
            Glide.with(activity).load("http://image.tmdb.org/t/p/w185/"+movie?.posterPath).into(foto)

            itemView.setOnClickListener({
                var intent:Intent = Intent(activity, MovieDetailActivity::class.java)
                //intent.putExtra("user",actualUser)
                intent.putExtra("movie",movie)
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