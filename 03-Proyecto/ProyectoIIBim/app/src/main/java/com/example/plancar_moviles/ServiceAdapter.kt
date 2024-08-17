package com.example.plancar_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Service(val imageResId: Int, val name: String, val price: String, val category: String)

class ServiceAdapter(private val services: List<Service>,
                     private val onItemClick: (Service) -> Unit  // Paso de la función onItemClick como parámetro
                    ) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceImage: ImageView = itemView.findViewById(R.id.service_image)
        val serviceName: TextView = itemView.findViewById(R.id.service_name)
        val servicePrice: TextView = itemView.findViewById(R.id.service_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.serviceImage.setImageResource(service.imageResId)
        holder.serviceName.text = service.name
        holder.servicePrice.text = service.price

        holder.itemView.setOnClickListener {
            onItemClick(service)  // Llamar la función cuando se hace clic en un servicio
        }
    }

    override fun getItemCount(): Int {
        return services.size
    }
}
