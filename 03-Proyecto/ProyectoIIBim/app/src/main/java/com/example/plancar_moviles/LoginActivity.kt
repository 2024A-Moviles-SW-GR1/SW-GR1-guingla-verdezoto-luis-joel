package com.example.plancar_moviles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    // Credenciales quemadas
    private val correctUsername = "joel"
    private val correctPassword = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText: EditText = findViewById(R.id.et_username)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val tvRegister: TextView = findViewById(R.id.tv_register)

        btnLogin.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            if (UserManager.isUserValid(enteredUsername, enteredPassword) || (enteredUsername == correctUsername && enteredPassword == correctPassword)) {
                // Credenciales correctas, redirigir a ServiciosActivity
                Toast.makeText(this, "Haz iniciado sesión correctamente", Toast.LENGTH_SHORT).show()
                // Obtener el usuario actual para pasarlo a la siguiente actividad
                val currentUser = UserManager.getCurrentUser()

                val intent = Intent(this, ServiciosActivity::class.java).apply {
                    putExtra("username", currentUser?.username ?: "Usuario")
                }
                startActivity(intent)
            } else {
                // Credenciales incorrectas, mostrar mensaje de error
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
