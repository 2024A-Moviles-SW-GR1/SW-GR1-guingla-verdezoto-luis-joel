package com.example.a2024aswgr1sqda

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<android.widget.ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, //layout xml a usar
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() //Actualiza UI
        val botonAnadirListView = findViewById<android.widget.Button>(R.id.btn_anadir_list_view)
        botonAnadirListView
            .setOnClickListener {
                anadirEntrenador(adaptador)
            }
        registerForContextMenu(listView) //Nueva linea

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_blist_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    var posicionItemSeleccionado = -1
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // llenamos opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                abrirDialogo() //Nueva linea
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                mostrarSnackbar(
                    "Acepto $which"
                )
            }
        )
        builder.setNegativeButton(
            "Cancelar", null)
        val opciones = resources.getStringArray(
            R.array.dias_semana
        )
        val seleccionPrevia = booleanArrayOf(
            true, //Lunes
            false, //Martes
            false,  //Miercoles
        )

        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {
                    dialog, which, isChecked ->
                mostrarSnackbar(
                    "Item: $which"
                )
            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }

    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ) {
        val nuevoEntrenador = BEntrenador(
            arreglo.size + 1,
            "Entrenador ${arreglo.size + 1}",
            "Descripcion Entrenador ${arreglo.size + 1}"
        )
        arreglo.add(nuevoEntrenador)
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackbar(texto: String) {
        var snack = Snackbar.make(
            findViewById(R.id.cl_blist_view),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }



}