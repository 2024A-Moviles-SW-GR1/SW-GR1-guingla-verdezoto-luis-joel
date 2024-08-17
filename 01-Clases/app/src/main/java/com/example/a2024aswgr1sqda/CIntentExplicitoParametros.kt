package com.example.a2024aswgr1sqda

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cintent_explicito_parametros)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")
        val boton = findViewById<android.widget.Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener{devolverRespuesta()}

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_sqlite)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun devolverRespuesta(){
        val intentDevolverRespuesta = Intent()
        intentDevolverRespuesta.putExtra("nombreModificado","Vicente")
        //devolver mas parametros si queremos
        setResult(RESULT_OK, intentDevolverRespuesta)
        finish()
    }
}