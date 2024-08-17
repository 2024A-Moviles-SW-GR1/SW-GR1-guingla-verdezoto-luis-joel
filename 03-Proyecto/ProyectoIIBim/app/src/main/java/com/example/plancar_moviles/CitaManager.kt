package com.example.plancar_moviles

object CitaManager {
    private val citasPendientes = mutableListOf<Cita>()

    fun addCita(cita: Cita) {
        citasPendientes.add(cita)
    }

    fun getCitas(): List<Cita> {
        return citasPendientes
    }

    fun removeCita(cita: Cita) {
        citasPendientes.remove(cita)
    }
}
