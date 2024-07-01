package com.example.a2024aswgr1sqda

class BBaseDatosMemoria(
) {
    //Companion Object
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(
                        1,
                        "Entrenador 1",
                        "Descripcion Entrenador 1"
                    )
                )
            arregloBEntrenador
                .add(
                    BEntrenador(
                        2,
                        "Entrenador 2",
                        "Descripcion Entrenador 2"
                    )
                )
            arregloBEntrenador
                .add(
                    BEntrenador(
                        3,
                        "Entrenador 3",
                        "Descripcion Entrenador 3"
                    )
                )
        }
    }
}