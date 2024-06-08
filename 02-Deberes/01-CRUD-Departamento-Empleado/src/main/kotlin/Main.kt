import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val projectDir = System.getProperty("user.dir")
val dataFile = File("$projectDir/src/main/kotlin/datos.txt")

// Entidad Departamento
data class Departamento(
    val nombre: String,
    val ubicacion: String,
    val fechaCreacion: LocalDate,
    val estaActivo: Boolean,
    val presupuesto: Double
) {
    val empleados = mutableListOf<Empleado>()
}

// Entidad Empleado
data class Empleado(
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate,
    val salario: Double,
    val esGerente: Boolean,
    var departamento: Departamento? = null
)

// Funciones CRUD para Departamento
fun crearDepartamento(departamentos: MutableList<Departamento>): Departamento {
    print("Ingrese el nombre del departamento: ")
    val nombre = readLine() ?: ""
    print("Ingrese la ubicación del departamento: ")
    val ubicacion = readLine() ?: ""
    print("Ingrese la fecha de creación (dd/MM/yyyy): ")
    val fechaCreacion = readLine()?.let { LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
    print("¿Está activo el departamento? (true/false): ")
    val estaActivo = readLine()?.toBoolean() ?: false
    print("Ingrese el presupuesto del departamento: ")
    val presupuesto = readLine()?.toDouble() ?: 0.0

    val departamento = Departamento(nombre, ubicacion, fechaCreacion ?: LocalDate.now(), estaActivo, presupuesto)
    departamentos.add(departamento)
    return departamento
}

fun leerDepartamentos(departamentos: List<Departamento>) {
    if (departamentos.isEmpty()) {
        println("No hay departamentos registrados.")
    } else {
        println("Departamentos registrados:")
        departamentos.forEach {
            println("Nombre: ${it.nombre}, Ubicación: ${it.ubicacion}, Fecha de creación: ${it.fechaCreacion}, Activo: ${it.estaActivo}, Presupuesto: ${it.presupuesto}")
        }
    }
}

fun actualizarDepartamento(departamentos: MutableList<Departamento>) {
    print("Ingrese el nombre del departamento a actualizar: ")
    val nombre = readLine() ?: ""
    val departamento = departamentos.find { it.nombre == nombre }

    if (departamento != null) {
        print("Ingrese el nuevo nombre del departamento (dejar en blanco para mantener el actual): ")
        val nuevoNombre = readLine()?.ifBlank { departamento.nombre } ?: departamento.nombre
        print("Ingrese la nueva ubicación del departamento (dejar en blanco para mantener la actual): ")
        val nuevaUbicacion = readLine()?.ifBlank { departamento.ubicacion } ?: departamento.ubicacion
        print("Ingrese la nueva fecha de creación (dd/MM/yyyy) (dejar en blanco para mantener la actual): ")
        val nuevaFechaCreacion = readLine()?.let {
            if (it.isNotBlank()) LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy")) else departamento.fechaCreacion
        }
        print("¿Está activo el departamento? (true/false) (dejar en blanco para mantener el estado actual): ")
        val nuevoEstado = readLine()?.toBoolean() ?: departamento.estaActivo
        print("Ingrese el nuevo presupuesto del departamento (dejar en blanco para mantener el actual): ")
        val entrada = readLine()
        val nuevoPresupuesto = if (entrada.isNullOrBlank()) departamento.presupuesto else entrada.toDouble()

        departamentos.remove(departamento)
        departamentos.add(
            Departamento(
                nuevoNombre,
                nuevaUbicacion,
                nuevaFechaCreacion ?: departamento.fechaCreacion,
                nuevoEstado,
                nuevoPresupuesto
            )
        )
        println("Departamento actualizado correctamente.")
    } else {
        println("No se encontró el departamento con el nombre especificado.")
    }
}

fun eliminarDepartamento(departamentos: MutableList<Departamento>) {
    print("Ingrese el nombre del departamento a eliminar: ")
    val nombre = readLine() ?: ""
    val departamento = departamentos.find { it.nombre == nombre }

    if (departamento != null) {
        departamentos.remove(departamento)
        println("Departamento eliminado correctamente.")
    } else {
        println("No se encontró el departamento con el nombre especificado.")
    }
}

// Funciones CRUD para Empleado
fun crearEmpleado(departamentos: MutableList<Departamento>): Empleado {
    print("Ingrese el nombre del empleado: ")
    val nombre = readLine() ?: ""
    print("Ingrese el apellido del empleado: ")
    val apellido = readLine() ?: ""
    print("Ingrese la fecha de nacimiento (dd/MM/yyyy): ")
    val fechaNacimiento = readLine()?.let { LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
    print("Ingrese el salario del empleado: ")
    val salario = readLine()?.toDouble() ?: 0.0
    print("¿Es gerente el empleado? (true/false): ")
    val esGerente = readLine()?.toBoolean() ?: false

    print("Ingrese el nombre del departamento al que pertenece el empleado: ")
    val nombreDepartamento = readLine() ?: ""
    val departamento = departamentos.find { it.nombre == nombreDepartamento }

    if (departamento != null) {
        val empleado = Empleado(nombre, apellido, fechaNacimiento ?: LocalDate.now(), salario, esGerente, departamento)
        departamento.empleados.add(empleado)
        return empleado
    } else {
        println("No se encontró el departamento con el nombre especificado.")
        return Empleado(nombre, apellido, fechaNacimiento ?: LocalDate.now(), salario, esGerente)
    }
}

fun leerEmpleados(departamentos: List<Departamento>) {
    if (departamentos.isEmpty()) {
        println("No hay departamentos registrados.")
    } else {
        departamentos.forEach { departamento ->
            println("Departamento: ${departamento.nombre}")
            if (departamento.empleados.isEmpty()) {
                println("No hay empleados registrados en este departamento.")
            } else {
                println("Empleados:")
                departamento.empleados.forEach {
                    println("Nombre: ${it.nombre}, Apellido: ${it.apellido}, Fecha de nacimiento: ${it.fechaNacimiento}, Salario: ${it.salario}, Gerente: ${it.esGerente}")
                }
            }
            println()
        }
    }
}

fun actualizarEmpleado(departamentos: MutableList<Departamento>) {
    print("Ingrese el nombre del empleado a actualizar: ")
    val nombre = readLine() ?: ""
    print("Ingrese el apellido del empleado a actualizar: ")
    val apellido = readLine() ?: ""

    val empleado = departamentos.flatMap { it.empleados }.find { it.nombre == nombre && it.apellido == apellido }

    if (empleado != null) {
        print("Ingrese el nuevo nombre del empleado (dejar en blanco para mantener el actual): ")
        val nuevoNombre = readLine()?.ifBlank { empleado.nombre } ?: empleado.nombre
        print("Ingrese el nuevo apellido del empleado (dejar en blanco para mantener el actual): ")
        val nuevoApellido = readLine()?.ifBlank { empleado.apellido } ?: empleado.apellido
        print("Ingrese la nueva fecha de nacimiento (dd/MM/yyyy) (dejar en blanco para mantener la actual): ")
        val nuevaFechaNacimiento = readLine()?.let {
            if (it.isNotBlank()) LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy")) else empleado.fechaNacimiento
        }
        print("Ingrese el nuevo salario del empleado (dejar en blanco para mantener el actual): ")
        val entrada = readLine()
        val nuevoSalario = if (entrada.isNullOrBlank()) empleado.salario else entrada.toDouble()
        print("¿Es gerente el empleado? (true/false) (dejar en blanco para mantener el estado actual): ")
        val nuevoEsGerente = readLine()?.toBoolean() ?: empleado.esGerente

        val departamento = empleado.departamento
        departamento?.empleados?.remove(empleado)

        val nuevoEmpleado = Empleado(
            nuevoNombre,
            nuevoApellido,
            nuevaFechaNacimiento ?: empleado.fechaNacimiento,
            nuevoSalario,
            nuevoEsGerente,
            departamento
        )

        departamento?.empleados?.add(nuevoEmpleado)
        println("Empleado actualizado correctamente.")
    } else {
        println("No se encontró el empleado con el nombre y apellido especificados.")
    }
}

fun eliminarEmpleado(departamentos: MutableList<Departamento>) {
    print("Ingrese el nombre del empleado a eliminar: ")
    val nombre = readLine() ?: ""
    print("Ingrese el apellido del empleado a eliminar: ")
    val apellido = readLine() ?: ""

    val empleado = departamentos.flatMap { it.empleados }.find { it.nombre == nombre && it.apellido == apellido }

    if (empleado != null) {
        val departamento = empleado.departamento
        departamento?.empleados?.remove(empleado)
        println("Empleado eliminado correctamente.")
    } else {
        println("No se encontró el empleado con el nombre y apellido especificados.")
    }
}

fun guardarDatos(departamentos: List<Departamento>, archivo: File) {
    archivo.bufferedWriter().use { writer ->
        departamentos.forEach { departamento ->
            writer.write("${departamento.nombre},${departamento.ubicacion},${departamento.fechaCreacion},${departamento.estaActivo},${departamento.presupuesto}\n")
            departamento.empleados.forEach { empleado ->
                writer.write("${empleado.nombre},${empleado.apellido},${empleado.fechaNacimiento},${empleado.salario},${empleado.esGerente},${departamento.nombre}\n")
            }
        }
    }
}

fun cargarDatos(archivo: File): List<Departamento> {
    val departamentos = mutableListOf<Departamento>()
    archivo.bufferedReader().useLines { lines ->
        lines.forEach { linea ->
            val datos = linea.split(",")
            when (datos.size) {
                5 -> {
                    val departamento = Departamento(
                        datos[0], datos[1], LocalDate.parse(datos[2]), datos[3].toBoolean(), datos[4].toDouble()
                    )
                    departamentos.add(departamento)
                }
                6 -> {
                    val departamento = departamentos.find { it.nombre == datos[5] }
                    if (departamento != null) {
                        val empleado = Empleado(
                            datos[0], datos[1], LocalDate.parse(datos[2]), datos[3].toDouble(), datos[4].toBoolean(), departamento
                        )
                        departamento.empleados.add(empleado)
                    }
                }
            }
        }
    }
    return departamentos
}

fun main() {
    val departamentos = mutableListOf<Departamento>()

    if (dataFile.exists()) {
        departamentos.addAll(cargarDatos(dataFile))
    }

    while (true) {
        println("Seleccione una opción:")
        println("1. Crear departamento")
        println("2. Leer departamentos")
        println("3. Actualizar departamento")
        println("4. Eliminar departamento")
        println("5. Crear empleado")
        println("6. Leer empleados")
        println("7. Actualizar empleado")
        println("8. Eliminar empleado")
        println("9. Salir")
        println("10. Guardar datos")

        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            1 -> crearDepartamento(departamentos)
            2 -> leerDepartamentos(departamentos)
            3 -> actualizarDepartamento(departamentos)
            4 -> eliminarDepartamento(departamentos)
            5 -> crearEmpleado(departamentos)
            6 -> leerEmpleados(departamentos)
            7 -> actualizarEmpleado(departamentos)
            8 -> eliminarEmpleado(departamentos)
            9 -> break
            10 -> guardarDatos(departamentos, dataFile)
            else -> println("Opción no válida")
        }
        println()
    }
}