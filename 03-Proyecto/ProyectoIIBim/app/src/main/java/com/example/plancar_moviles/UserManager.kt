package com.example.plancar_moviles

object UserManager {
    val registeredUsers = mutableListOf<User>()
    private var currentUser: User? = null  // Usuario actualmente logueado

    fun addUser(user: User) {
        registeredUsers.add(user)
    }

    fun isUserValid(username: String, password: String): Boolean {
        val user = registeredUsers.find { it.username == username && it.password == password }
        if (user != null) {
            currentUser = user  // Asigna el usuario como el actual si es válido
            return true
        }
        return false
    }

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun logout() {
        currentUser = null  // Limpiar el usuario actual al hacer logout
    }
}

// Clase User que se utilizará en toda la aplicación
data class User(val username: String, val name: String, val contact: String, val password: String)
