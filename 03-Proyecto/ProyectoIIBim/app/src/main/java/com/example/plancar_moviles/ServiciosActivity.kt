package com.example.plancar_moviles

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import java.util.Calendar

class ServiciosActivity : AppCompatActivity() {

    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var serviceList: List<Service>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)

        // Obtener el nombre de usuario del UserManager
        val username = UserManager.getCurrentUser()?.username ?: "Usuario"
        val tvUsername: TextView = findViewById(R.id.tv_username)

        // Configurar el nombre de usuario en el TextView
        tvUsername.text = username

        // Simulación de servicios
        serviceList = listOf(
            Service(R.drawable.alineacion_neumaticos, "Alineación de neumáticos", "$20.15", "Mantenimiento Preventivo"),
            Service(R.drawable.cambio_aceite, "Cambio de aceite", "$42.55", "Mantenimiento Preventivo"),
            Service(R.drawable.reparacion_motor, "Reparación de motor", "$200.00", "Reparaciones Mecánicas"),
            Service(R.drawable.reparacion_escape, "Reparación de escape", "$150.00", "Reparaciones Mecánicas"),
            Service(R.drawable.revision_frenos, "Revisión de frenos", "$60.00", "Diagnóstico y Revisión"),
            Service(R.drawable.revision_emisiones, "Revisión de emisiones", "$75.00", "Diagnóstico y Revisión")
        )

        // Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // Aquí pasamos la función lambda que se ejecutará cuando se haga clic en un servicio
        serviceAdapter = ServiceAdapter(serviceList) { service ->
            showDatePicker(service)
        }
        recyclerView.adapter = serviceAdapter


        // Scroll Listener para cambiar el color de las secciones según la posición
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                // Obtener la categoría del primer y último elemento visible
                val firstVisibleServiceCategory = serviceList[firstVisibleItemPosition].category
                val lastVisibleServiceCategory = serviceList[lastVisibleItemPosition].category

                // Verificar si ambos elementos visibles pertenecen a la misma categoría
                if (firstVisibleServiceCategory == lastVisibleServiceCategory) {
                    updateSectionHighlight(firstVisibleItemPosition)
                } else {
                    // En caso de que haya un cambio en la categoría visible, seleccionar la categoría intermedia
                    updateSectionHighlight(lastVisibleItemPosition)
                }
            }
        })


        // Configuración del SearchView
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterServices(newText ?: "")
                return true
            }
        })

        // Configuración del icono "Mis Citas" en el footer
        val iconCitas = findViewById<ImageView>(R.id.icon_citas)
        iconCitas.setOnClickListener {
            val intent = Intent(this, MisCitasActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        // En caso de que quieras manejar también la navegación a la pantalla de Cuenta
        val iconCuenta = findViewById<ImageView>(R.id.icon_cuenta)
        iconCuenta.setOnClickListener {
            // Lógica para ir a la pantalla de Cuenta
        }
    }

    // Método para filtrar servicios según la categoría
    private fun updateSectionHighlight(position: Int) {
        val tvSection1 = findViewById<TextView>(R.id.tv_section1)
        val tvSection1_2 = findViewById<TextView>(R.id.tv_section1_2)
        val tvSection2 = findViewById<TextView>(R.id.tv_section2)
        val tvSection2_2 = findViewById<TextView>(R.id.tv_section2_2)
        val tvSection3 = findViewById<TextView>(R.id.tv_section3)
        val tvSection3_2 = findViewById<TextView>(R.id.tv_section3_2)

        when (serviceList[position].category) {
            "Mantenimiento Preventivo" -> {
                tvSection1.setTextColor(resources.getColor(R.color.blue))
                tvSection1_2.setTextColor(resources.getColor(R.color.blue))
                tvSection2.setTextColor(resources.getColor(R.color.gray))
                tvSection2_2.setTextColor(resources.getColor(R.color.gray))
                tvSection3.setTextColor(resources.getColor(R.color.gray))
                tvSection3_2.setTextColor(resources.getColor(R.color.gray))
            }
            "Reparaciones Mecánicas" -> {
                tvSection1.setTextColor(resources.getColor(R.color.gray))
                tvSection1_2.setTextColor(resources.getColor(R.color.gray))
                tvSection2.setTextColor(resources.getColor(R.color.blue))
                tvSection2_2.setTextColor(resources.getColor(R.color.blue))
                tvSection3.setTextColor(resources.getColor(R.color.gray))
                tvSection3_2.setTextColor(resources.getColor(R.color.gray))
            }
            "Diagnóstico y Revisión" -> {
                tvSection1.setTextColor(resources.getColor(R.color.gray))
                tvSection1_2.setTextColor(resources.getColor(R.color.gray))
                tvSection2.setTextColor(resources.getColor(R.color.gray))
                tvSection2_2.setTextColor(resources.getColor(R.color.gray))
                tvSection3.setTextColor(resources.getColor(R.color.blue))
                tvSection3_2.setTextColor(resources.getColor(R.color.blue))
            }
        }
    }

    // Filtrar los servicios según el texto ingresado en el SearchView
    private fun filterServices(query: String) {
        val filteredList = serviceList.filter { it.name.contains(query, true) }
        // Crea un nuevo adaptador con la lista filtrada y el mismo lambda para los clics
        serviceAdapter = ServiceAdapter(filteredList) { service ->
            showDatePicker(service)
        }

        findViewById<RecyclerView>(R.id.recycler_view).adapter = serviceAdapter

        if (query.isNotEmpty()) {
            // Si hay un texto de búsqueda, ponemos las secciones en gris
            setSectionsToGray()
        } else {
            // Si el texto de búsqueda está vacío, volvemos al comportamiento normal
            if (filteredList.isNotEmpty()) {
                updateSectionHighlight(0) // Usamos la primera posición como referencia
            }
        }
    }

    // Función para poner las secciones en gris
    private fun setSectionsToGray() {
        val tvSection1 = findViewById<TextView>(R.id.tv_section1)
        val tvSection1_2 = findViewById<TextView>(R.id.tv_section1_2)
        val tvSection2 = findViewById<TextView>(R.id.tv_section2)
        val tvSection2_2 = findViewById<TextView>(R.id.tv_section2_2)
        val tvSection3 = findViewById<TextView>(R.id.tv_section3)
        val tvSection3_2 = findViewById<TextView>(R.id.tv_section3_2)

        tvSection1.setTextColor(resources.getColor(R.color.gray))
        tvSection1_2.setTextColor(resources.getColor(R.color.gray))
        tvSection2.setTextColor(resources.getColor(R.color.gray))
        tvSection2_2.setTextColor(resources.getColor(R.color.gray))
        tvSection3.setTextColor(resources.getColor(R.color.gray))
        tvSection3_2.setTextColor(resources.getColor(R.color.gray))
    }

    private fun showDatePicker(service: Service) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                saveCita(service, selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun saveCita(service: Service, date: String) {
        val cita = Cita(CitaManager.getCitas().size + 1, service.name, date, service.price)
        CitaManager.addCita(cita)  // Añadir la cita a la lista en CitaManager

        // Mostrar un mensaje o hacer alguna acción adicional si es necesario
        Toast.makeText(this, "Cita registrada: ${service.name} el $date", Toast.LENGTH_SHORT).show()
    }
}

data class Cita(val numero: Int, val servicio: String, val fecha: String, val precio: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numero)
        parcel.writeString(servicio)
        parcel.writeString(fecha)
        parcel.writeString(precio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cita> {
        override fun createFromParcel(parcel: Parcel): Cita {
            return Cita(parcel)
        }

        override fun newArray(size: Int): Array<Cita?> {
            return arrayOfNulls(size)
        }
    }
}

