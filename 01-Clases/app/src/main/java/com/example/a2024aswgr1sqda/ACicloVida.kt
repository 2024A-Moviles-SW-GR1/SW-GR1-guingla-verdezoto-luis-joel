package com.example.a2024aswgr1sqda

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    var textoGlobal = ""
    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        var snack = Snackbar.make(
            findViewById(R.id.id_layout_main),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()

        val botonIntendImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntendImplicito
            .setOnClickListener {
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackContenidoIntentImplicito.launch(intentConRespuesta)
            }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener {
                val intentExplicito = Intent(
                    this,
                    CIntentExplicitoParametros::class.java
                )
                intentExplicito.putExtra("nombre", "Adrian")
                intentExplicito.putExtra("apellido", "Eguez")
                intentExplicito.putExtra("edad", 29)
                intentExplicito.putExtra("entrenador", BEntrenador(1,"Adrian", "Eguez"))
                callbackContenidoIntentExplicito.launch(intentExplicito)
            }

        //Inicializar Base de Datos
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)
        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite.setOnClickListener{
            irActividad(ECrudEntrenador::class.java)
        }
        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView.setOnClickListener{
            irActividad(FRecyclerView::class.java)
        }

        val BotonGMaps = findViewById<Button>(R.id.btn_google_maps)
        BotonGMaps.setOnClickListener{
            irActividad(GGoogleMapsActivity::class.java)
        }

        val BotonFirebaseUI  = findViewById<Button>(R.id.btn_intent_firebase_ui)
        BotonFirebaseUI.setOnClickListener{
            irActividad(HFirebaseUIAuth::class.java)
        }


    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if (result.data != null) {
                    // logica negocio
                    val data = result.data;
                    mostrarSnackbar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }

    val callbackContenidoIntentImplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if (result.data != null) {
                    // logica negocio
                    if (result.data!!.data!= null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono: ${telefono}")
                    }
                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_layout_main)) { v, insets ->
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

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}