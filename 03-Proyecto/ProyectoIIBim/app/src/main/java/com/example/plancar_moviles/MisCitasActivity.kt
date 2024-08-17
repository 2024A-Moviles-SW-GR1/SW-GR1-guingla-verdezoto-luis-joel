package com.example.plancar_moviles

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MisCitasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mis_citas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener el nombre de usuario del UserManager
        val username = UserManager.getCurrentUser()?.username ?: "Usuario"
        val tvUsername: TextView = findViewById(R.id.tv_username)

        // Configurar el nombre de usuario en el TextView
        tvUsername.text = username

        // Obtener las citas pendientes desde el intent
        val citasPendientes = CitaManager.getCitas()

        if (citasPendientes.isEmpty()) {
            // Mostrar un mensaje o una pantalla que indique que no hay citas
            Toast.makeText(this, "No tienes citas pendientes.", Toast.LENGTH_SHORT).show()
        } else {
            val recyclerView: RecyclerView = findViewById(R.id.recycler_view_citas)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = CitasAdapter(citasPendientes.toMutableList())
            recyclerView.adapter = adapter
        }

        // Configuración del icono "Servicios" en el footer
        val iconServicios = findViewById<ImageView>(R.id.icon_servicios)
        iconServicios.setOnClickListener {
            val intent = Intent(this, ServiciosActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)  // Para una transición suave sin animaciones
        }

        // Configuración del icono "Cuenta" si es necesario
        val iconCuenta = findViewById<ImageView>(R.id.icon_cuenta)
        iconCuenta.setOnClickListener {
            // Lógica para ir a la pantalla de Cuenta (si la tienes implementada)
        }
    }
}
