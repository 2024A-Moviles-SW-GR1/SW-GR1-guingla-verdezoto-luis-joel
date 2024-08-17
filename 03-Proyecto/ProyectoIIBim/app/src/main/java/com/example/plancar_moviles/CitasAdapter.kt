package com.example.plancar_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitasAdapter(private var citas: MutableList<Cita>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPosition = -1 // Indica qué item está seleccionado para mostrar el menú

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numeroTextView: TextView = itemView.findViewById(R.id.tv_numero)
        val servicioTextView: TextView = itemView.findViewById(R.id.tv_servicio)
        val fechaTextView: TextView = itemView.findViewById(R.id.tv_fecha)
        val precioTextView: TextView = itemView.findViewById(R.id.tv_precio)
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eliminarCitaButton: Button = itemView.findViewById(R.id.btn_eliminar_cita)
        val cancelarAccionButton: Button = itemView.findViewById(R.id.btn_cancelar_accion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
            CitaViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita_menu, parent, false)
            MenuViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val actualPosition = holder.adapterPosition

        if (holder is CitaViewHolder) {
            val cita = citas[actualPosition]
            holder.numeroTextView.text = (actualPosition + 1).toString()
            holder.servicioTextView.text = cita.servicio
            holder.fechaTextView.text = cita.fecha
            holder.precioTextView.text = cita.precio

            holder.itemView.setOnClickListener {
                selectedPosition = actualPosition
                notifyDataSetChanged() // Actualiza el adaptador para mostrar el menú
            }

        } else if (holder is MenuViewHolder) {
            holder.eliminarCitaButton.setOnClickListener {
                CitaManager.removeCita(citas[actualPosition])  // Eliminar de CitaManager
                citas.removeAt(actualPosition)
                selectedPosition = -1
                notifyDataSetChanged() // Actualiza la lista después de eliminar
            }

            holder.cancelarAccionButton.setOnClickListener {
                selectedPosition = -1
                notifyDataSetChanged() // Vuelve al estado original
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == selectedPosition) 1 else 0
    }

    override fun getItemCount(): Int {
        return citas.size
    }
}

