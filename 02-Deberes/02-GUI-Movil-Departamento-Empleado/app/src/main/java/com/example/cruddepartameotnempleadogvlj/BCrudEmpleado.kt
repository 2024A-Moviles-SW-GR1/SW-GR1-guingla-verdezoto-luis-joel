package com.example.cruddepartameotnempleadogvlj

import android.app.AlertDialog
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class BCrudEmpleado : AppCompatActivity() {

    private lateinit var sqliteHelper: ESqliteHelperDepartamento
    private var departamentoId: Int? = null
    private var empleadoSeleccionado: BEmpleado? = null
    private lateinit var lvEmpleados: ListView
    private lateinit var btnCrear: Button
    private lateinit var btnConfirmarEdicion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bcrud_empleado)

        sqliteHelper = ESqliteHelperDepartamento(this)
        departamentoId = intent.getIntExtra("departamentoId", -1)

        val etNombre = findViewById<EditText>(R.id.et_nombre_empleado)
        val etApellido = findViewById<EditText>(R.id.et_apellido_empleado)
        val etFechaNacimiento = findViewById<EditText>(R.id.et_fecha_nacimiento_empleado)
        val etSalario = findViewById<EditText>(R.id.et_salario_empleado)
        val cbEsGerente = findViewById<CheckBox>(R.id.cb_es_gerente_empleado)
        val spDepartamento = findViewById<Spinner>(R.id.sp_departamento_empleado)

        btnCrear = findViewById(R.id.btn_crear_empleado)
        btnConfirmarEdicion = findViewById(R.id.btn_confirmar_edicion_empleado)

        lvEmpleados = findViewById(R.id.lv_empleados)
        registerForContextMenu(lvEmpleados)

        // Cargar los departamentos en el Spinner
        cargarDepartamentosEnSpinner(spDepartamento)

        btnCrear.setOnClickListener {
            departamentoId?.let { id ->
                val empleado = BEmpleado(
                    id = null,
                    nombre = etNombre.text.toString(),
                    apellido = etApellido.text.toString(),
                    fechaNacimiento = LocalDate.parse(etFechaNacimiento.text.toString()),
                    salario = etSalario.text.toString().toDouble(),
                    esGerente = cbEsGerente.isChecked,
                    departamento = spDepartamento.selectedItem as BDepartamento
                )
                if (sqliteHelper.crearEmpleado(empleado)) {
                    Toast.makeText(this, "Empleado creado", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    mostrarEmpleados()
                } else {
                    Toast.makeText(this, "Error al crear empleado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnConfirmarEdicion.setOnClickListener {
            empleadoSeleccionado?.let { empleado ->
                empleado.nombre = etNombre.text.toString()
                empleado.apellido = etApellido.text.toString()
                empleado.fechaNacimiento = LocalDate.parse(etFechaNacimiento.text.toString())
                empleado.salario = etSalario.text.toString().toDouble()
                empleado.esGerente = cbEsGerente.isChecked
                empleado.departamento = spDepartamento.selectedItem as BDepartamento
                if (sqliteHelper.actualizarEmpleado(empleado)) {
                    Toast.makeText(this, "Empleado actualizado", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                    mostrarEmpleados()
                } else {
                    Toast.makeText(this, "Error al actualizar empleado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mostrarEmpleados()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_empleado, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val empleado = lvEmpleados.getItemAtPosition(info.position) as BEmpleado

        return when (item.itemId) {
            R.id.context_menu_editar_empleado -> {
                empleadoSeleccionado = empleado
                findViewById<EditText>(R.id.et_nombre_empleado).setText(empleado.nombre)
                findViewById<EditText>(R.id.et_apellido_empleado).setText(empleado.apellido)
                findViewById<EditText>(R.id.et_fecha_nacimiento_empleado).setText(empleado.fechaNacimiento.toString())
                findViewById<EditText>(R.id.et_salario_empleado).setText(empleado.salario.toString())
                findViewById<CheckBox>(R.id.cb_es_gerente_empleado).isChecked = empleado.esGerente
                findViewById<Spinner>(R.id.sp_departamento_empleado).setSelection(obtenerPosicionDepartamento(empleado.departamento, findViewById(R.id.sp_departamento_empleado)))

                btnCrear.visibility = View.GONE
                btnConfirmarEdicion.visibility = View.VISIBLE
                true
            }
            R.id.context_menu_eliminar_empleado -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Confirmar Eliminación")
                    setMessage("¿Estás seguro de que deseas eliminar este empleado?")
                    setPositiveButton("Continuar") { _, _ ->
                        if (sqliteHelper.eliminarEmpleado(empleado.id!!)) {
                            Toast.makeText(this@BCrudEmpleado, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                            limpiarCampos()
                            mostrarEmpleados()
                        } else {
                            Toast.makeText(this@BCrudEmpleado, "Error al eliminar empleado", Toast.LENGTH_SHORT).show()
                        }
                    }
                    setNegativeButton("Cancelar", null)
                }.create().show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun cargarDepartamentosEnSpinner(spDepartamento: Spinner) {
        val departamentos = sqliteHelper.leerTodosLosDepartamentos()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDepartamento.adapter = adapter
    }

    private fun limpiarCampos() {
        findViewById<EditText>(R.id.et_nombre_empleado).setText("")
        findViewById<EditText>(R.id.et_apellido_empleado).setText("")
        findViewById<EditText>(R.id.et_fecha_nacimiento_empleado).setText("")
        findViewById<EditText>(R.id.et_salario_empleado).setText("")
        findViewById<CheckBox>(R.id.cb_es_gerente_empleado).isChecked = false
        findViewById<Spinner>(R.id.sp_departamento_empleado).setSelection(0)
        btnCrear.visibility = View.VISIBLE
        btnConfirmarEdicion.visibility = View.GONE
        empleadoSeleccionado = null
    }

    private fun mostrarEmpleados() {
        departamentoId?.let { id ->
            val empleados = sqliteHelper.leerTodosLosEmpleados().filter { it.departamento?.id == id }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, empleados)
            lvEmpleados.adapter = adapter
        }
    }

    private fun obtenerPosicionDepartamento(departamento: BDepartamento?, spDepartamento: Spinner): Int {
        for (i in 0 until spDepartamento.count) {
            if ((spDepartamento.getItemAtPosition(i) as BDepartamento).id == departamento?.id) {
                return i
            }
        }
        return 0
    }
}
