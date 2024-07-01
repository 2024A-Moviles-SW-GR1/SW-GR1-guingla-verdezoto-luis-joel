package com.example.a2024aswgr1sqda

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.persistableBundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        var snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mostrarSnackbar("onCreate")
    }

    override fun onStart(){
        super.onStart()
        mostrarSnackbar("OnStart")
    }
    override fun onResume(){
        super.onResume()
        mostrarSnackbar("OnResume")
    }

    override fun onRestart(){
        super.onRestart()
        mostrarSnackbar("OnRestart")
    }
    override fun onPause(){
        super.onPause()
        mostrarSnackbar("OnPause")
    }
    override fun onStop(){
        super.onStop()
        mostrarSnackbar("OnStop")
    }
    override fun onDestroy(){
        super.onDestroy()
        mostrarSnackbar("OnDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle ) {
        outState
            .run {
                // GUARDAR LAS PRIMITIVAS
                putString("variableTextoGuardado", textoGlobal)
            }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LA VARIABLE
        val textoRecuperadoDeVariable: String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperadoDeVariable!=null) {
            mostrarSnackbar(textoRecuperadoDeVariable)
            textoGlobal = textoRecuperadoDeVariable
        }
    }



}