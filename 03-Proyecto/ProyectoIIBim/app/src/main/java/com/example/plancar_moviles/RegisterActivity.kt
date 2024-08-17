package com.example.plancar_moviles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usernameEditText: EditText = findViewById(R.id.et_register_username)
        val nameEditText: EditText = findViewById(R.id.et_register_name)
        val contactEditText: EditText = findViewById(R.id.et_register_contact)
        val passwordEditText: EditText = findViewById(R.id.et_register_password)
        val btnRegister: Button = findViewById(R.id.btn_register)
        val tvLogin: TextView = findViewById(R.id.tv_login)

        btnRegister.setOnClickListener {
            val username = usernameEditText.text.toString()
            val name = nameEditText.text.toString()
            val contact = contactEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && name.isNotEmpty() && contact.isNotEmpty() && password.isNotEmpty()) {
                // Crear un nuevo usuario y agregarlo a la lista de usuarios registrados
                val newUser = User(username, name, contact, password)
                UserManager.addUser(newUser)

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

                // Redirigir a la pantalla de Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                // Mostrar mensaje de error si algún campo está vacío
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}

