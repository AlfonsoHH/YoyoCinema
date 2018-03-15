package com.example.alfonsohernandez.yoyocinema.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.alfonsohernandez.yoyocinema.R
import com.example.alfonsohernandez.yoyocinema.models.Movie
import com.example.alfonsohernandez.yoyocinema.models.User

/**
 * Created by alfonsohernandez on 15/03/2018.
 */
class MyAdapter(val movieList: MutableList<Movie>, val activity: Activity, var actualUser: User): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(movieList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_movies, parent, false)
        return ViewHolder(v, activity, actualUser)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class ViewHolder(itemView: View, val activity: Activity, var actualUser: User): RecyclerView.ViewHolder(itemView){


        fun bindItems(movie: Movie){

            val txtName = itemView.findViewById<TextView>(R.id.txtTitleRV)
            val txtTitle = itemView.findViewById<TextView>(R.id.txtDescriptionRV)
            val foto = itemView.findViewById<ImageView>(R.id.photoMovieRV)

            txtName?.text = movie.original_title
            txtTitle?.text = movie.overview
            /*when(reto.tipo){
                "Dieta" -> foto?.setImageResource(R.drawable.icono_dieta)
                "Ejercicio" -> foto?.setImageResource(R.drawable.icono_ejercicio)
                "Libros" -> foto?.setImageResource(R.drawable.icono_libros)
                "Amigos" -> foto?.setImageResource(R.drawable.icono_amigos)
                "Viajes" -> foto?.setImageResource(R.drawable.icono_viajar)
            }*/

            /*itemView.setOnClickListener({
                var intent:Intent = Intent(activity,RetoDetalleActivity::class.java)
                intent.putExtra("usuario",usuarioActual)
                intent.putExtra("reto",reto)
                activity.startActivity(intent)

            })

            itemView.setOnLongClickListener({
                val alertDilog = AlertDialog.Builder(activity).create()
                alertDilog.setTitle("Alert")
                alertDilog.setMessage("¿Estas seguro de abandonar este reto?")

                alertDilog.setButton(AlertDialog.BUTTON_POSITIVE, "Si", {
                    dialogInterface, i ->  ref.child(reto.idReto).removeValue().addOnCompleteListener{
                }

                })

                alertDilog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", {
                    dialogInterface, i ->
                })
                alertDilog.show()
                return@setOnLongClickListener true
            })
            */

        }
    }

}