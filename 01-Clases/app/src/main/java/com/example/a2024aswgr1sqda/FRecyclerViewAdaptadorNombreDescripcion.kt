package com.example.a2024aswgr1sqda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion (
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>(){
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nombreTextView: TextView
        val descripcionTextView: TextView
        val likesTextView: TextView
        var accionButton: Button
        var numeroLikes = 0
        init {
            nombreTextView = itemView.findViewById(R.id.tv_nombre)
            descripcionTextView = itemView.findViewById(R.id.tv_descripcion)
            likesTextView = itemView.findViewById(R.id.tv_likes)
            accionButton = itemView.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener {
                anadirLikes()
            }

        }
        fun anadirLikes(){
            numeroLikes++
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes()

        }
    }

    //Settear el layout que vamos a utilizar
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recylcler_view_vista,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    //Setear los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text = entrenadorActual.likes.toString()
        holder.accionButton = "ID: $entrenadorActual.id" + " Nombre: ${entrenadorActual.nombre}"

    }
}