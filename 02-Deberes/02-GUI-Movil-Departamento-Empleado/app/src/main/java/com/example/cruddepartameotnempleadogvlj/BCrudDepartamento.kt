package com.example.cruddepartameotnempleadogvlj

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class BCrudDepartamento : AppCompatActivity() {

    private lateinit var sqliteHelper: ESqliteHelperDepartamento
    private var departamentoSeleccionado: BDepartamento? = null
    private lateinit var lvDepartamentos: ListView
    private lateinit var btnCrear: Button
    private lateinit var btnConfirmarEdicion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bcrud_departamento)

        sqliteHelper = ESqliteHelperDepartamento(this)

        // Referencias a los elementos de la interfaz
        val etNombre = findViewById<EditText>(R.id.et_nombre_departamento)
        val etUbicacion = findViewById<EditText>(R.id.et_ubicacion_departamento)
        val etLatitud = findViewById<EditText>(R.id.et_latitud_departamento)
        val etLongitud = findViewById<EditText>(R.id.et_longitud_departamento)
        val etFechaCreacion = findViewById<EditText>(R.id.et_fecha_creacion_departamento)
        val etPresupuesto = findViewById<EditText>(R.id.et_presupuesto_departamento)
        val cbEstaActivo = findViewById<CheckBox>(R.id.cb_esta_activo_departamento)

        btnCrear = findViewById(R.id.btn_crear_departamento)
        btnConfirmarEdicion = findViewById(R.id.btn_confirmar_edicion_departamento)

        lvDepartamentos = findViewById(R.id.lv_departamentos)
        registerForContextMenu(lvDepartamentos)

        btnCrear.setOnClickListener {
            try {
                val departamento = BDepartamento(
                    id = null,
                    nombre = etNombre.text.toString(),
                    ubicacion = etUbicacion.text.toString(),
                    latitud = etLatitud.text.toString().toDoubleOrNull(),
                    longitud = etLongitud.text.toString().toDoubleOrNull(),
                    fechaCreacion = LocalDate.parse(etFechaCreacion.text.toString()),
                    estaActivo = cbEstaActivo.isChecked,
                    presupuesto = etPresupuesto.text.toString().toDouble()
                )
                if (sqliteHelper.crearDepartamento(departamento)) {
                    Toast.makeText(this, "Departamento creado", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    mostrarDepartamentos()
                } else {
                    Toast.makeText(this, "Error al crear departamento", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        btnConfirmarEdicion.setOnClickListener {
            departamentoSeleccionado?.let { departamento ->
                try {
                    departamento.nombre = etNombre.text.toString()
                    departamento.ubicacion = etUbicacion.text.toString()
                    departamento.latitud = etLatitud.text.toString().toDoubleOrNull()
                    departamento.longitud = etLongitud.text.toString().toDoubleOrNull()
                    departamento.fechaCreacion = LocalDate.parse(etFechaCreacion.text.toString())
                    departamento.estaActivo = cbEstaActivo.isChecked
                    departamento.presupuesto = etPresupuesto.text.toString().toDouble()
                    if (sqliteHelper.actualizarDepartamento(departamento)) {
                        Toast.makeText(this, "Departamento actualizado", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                        mostrarDepartamentos()
                    } else {
                        Toast.makeText(this, "Error al actualizar departamento", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        mostrarDepartamentos()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_departamento, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val departamento = lvDepartamentos.getItemAtPosition(info.position) as BDepartamento

        return when (item.itemId) {
            R.id.context_menu_editar_departamento -> {
                departamentoSeleccionado = departamento
                findViewById<EditText>(R.id.et_nombre_departamento).setText(departamento.nombre)
                findViewById<EditText>(R.id.et_ubicacion_departamento).setText(departamento.ubicacion)
                findViewById<EditText>(R.id.et_latitud_departamento).setText(departamento.latitud?.toString() ?: "")
                findViewById<EditText>(R.id.et_longitud_departamento).setText(departamento.longitud?.toString() ?: "")
                findViewById<EditText>(R.id.et_fecha_creacion_departamento).setText(departamento.fechaCreacion.toString())
                findViewById<EditText>(R.id.et_presupuesto_departamento).setText(departamento.presupuesto.toString())
                findViewById<CheckBox>(R.id.cb_esta_activo_departamento).isChecked = departamento.estaActivo

                btnCrear.visibility = View.GONE
                btnConfirmarEdicion.visibility = View.VISIBLE
                true
            }
            R.id.context_menu_eliminar_departamento -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Confirmar Eliminación")
                    setMessage("¿Estás seguro de que deseas eliminar este departamento?")
                    setPositiveButton("Continuar") { _, _ ->
                        if (sqliteHelper.eliminarDepartamento(departamento.id!!)) {
                            Toast.makeText(this@BCrudDepartamento, "Departamento eliminado", Toast.LENGTH_SHORT).show()
                            limpiarCampos()
                            mostrarDepartamentos()
                        } else {
                            Toast.makeText(this@BCrudDepartamento, "Error al eliminar departamento", Toast.LENGTH_SHORT).show()
                        }
                    }
                    setNegativeButton("Cancelar", null)
                }.create().show()
                true
            }
            R.id.context_menu_ver_empleados -> {
                val intent = Intent(this, BCrudEmpleado::class.java)
                intent.putExtra("departamentoId", departamento.id)
                startActivity(intent)
                true
            }
            R.id.context_menu_ver_ubicacion -> {
                val intent = Intent(this, MapaDepartamentoActivity::class.java)
                intent.putExtra("latitud", departamento.latitud ?: 0.0)
                intent.putExtra("longitud", departamento.longitud ?: 0.0)
                intent.putExtra("nombre", departamento.nombre)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)

        }
    }

    private fun limpiarCampos() {
        findViewById<EditText>(R.id.et_nombre_departamento).setText("")
        findViewById<EditText>(R.id.et_ubicacion_departamento).setText("")
        findViewById<EditText>(R.id.et_latitud_departamento).setText("")
        findViewById<EditText>(R.id.et_longitud_departamento).setText("")
        findViewById<EditText>(R.id.et_fecha_creacion_departamento).setText("")
        findViewById<EditText>(R.id.et_presupuesto_departamento).setText("")
        findViewById<CheckBox>(R.id.cb_esta_activo_departamento).isChecked = false
        btnCrear.visibility = View.VISIBLE
        btnConfirmarEdicion.visibility = View.GONE
        departamentoSeleccionado = null
    }

    private fun mostrarDepartamentos() {
        val departamentos = sqliteHelper.leerTodosLosDepartamentos()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, departamentos)
        lvDepartamentos.adapter = adapter
    }
}
