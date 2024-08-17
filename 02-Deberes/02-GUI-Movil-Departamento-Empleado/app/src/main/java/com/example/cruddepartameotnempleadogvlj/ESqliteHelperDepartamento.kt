package com.example.cruddepartameotnempleadogvlj

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate

class ESqliteHelperDepartamento(
    contexto: Context
) : SQLiteOpenHelper(
    contexto,
    "departamentos",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaDepartamento = """
            CREATE TABLE DEPARTAMENTO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NOMBRE TEXT,
                UBICACION TEXT,
                FECHA_CREACION TEXT,
                ESTA_ACTIVO INTEGER,
                PRESUPUESTO REAL
            )
        """.trimIndent()
        db?.execSQL(scriptCrearTablaDepartamento)

        val scriptCrearTablaEmpleado = """
            CREATE TABLE EMPLEADO (
                ID INTEGER PRIMARY KEY AUTOINCREMENT,
                NOMBRE TEXT,
                APELLIDO TEXT,
                FECHA_NACIMIENTO TEXT,
                SALARIO REAL,
                ES_GERENTE INTEGER,
                DEPARTAMENTO_ID INTEGER,
                FOREIGN KEY(DEPARTAMENTO_ID) REFERENCES DEPARTAMENTO(ID)
            )
        """.trimIndent()
        db?.execSQL(scriptCrearTablaEmpleado)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos si cambian las versiones
    }

    // Métodos para CRUD de Departamento

    fun crearDepartamento(departamento: BDepartamento): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", departamento.nombre)
            put("UBICACION", departamento.ubicacion)
            put("FECHA_CREACION", departamento.fechaCreacion.toString())
            put("ESTA_ACTIVO", if (departamento.estaActivo) 1 else 0)
            put("PRESUPUESTO", departamento.presupuesto)
        }
        val resultado = db.insert("DEPARTAMENTO", null, valores)
        db.close()
        return resultado != -1L
    }

    fun leerDepartamento(id: Int): BDepartamento? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM DEPARTAMENTO WHERE ID=?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val departamento = BDepartamento(
                id = cursor.getInt(0),
                nombre = cursor.getString(1),
                ubicacion = cursor.getString(2),
                fechaCreacion = LocalDate.parse(cursor.getString(3)),
                estaActivo = cursor.getInt(4) == 1,
                presupuesto = cursor.getDouble(5)
            )
            cursor.close()
            db.close()
            departamento
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun actualizarDepartamento(departamento: BDepartamento): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", departamento.nombre)
            put("UBICACION", departamento.ubicacion)
            put("FECHA_CREACION", departamento.fechaCreacion.toString())
            put("ESTA_ACTIVO", if (departamento.estaActivo) 1 else 0)
            put("PRESUPUESTO", departamento.presupuesto)
        }
        val resultado = db.update("DEPARTAMENTO", valores, "ID=?", arrayOf(departamento.id.toString()))
        db.close()
        return resultado > 0
    }

    fun eliminarDepartamento(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete("DEPARTAMENTO", "ID=?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }

    // Métodos para CRUD de Empleado

    fun crearEmpleado(empleado: BEmpleado): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", empleado.nombre)
            put("APELLIDO", empleado.apellido)
            put("FECHA_NACIMIENTO", empleado.fechaNacimiento.toString())
            put("SALARIO", empleado.salario)
            put("ES_GERENTE", if (empleado.esGerente) 1 else 0)
            put("DEPARTAMENTO_ID", empleado.departamento?.id)
        }
        val resultado = db.insert("EMPLEADO", null, valores)
        db.close()
        return resultado != -1L
    }

    fun leerEmpleado(id: Int): BEmpleado? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM EMPLEADO WHERE ID=?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val empleado = BEmpleado(
                id = cursor.getInt(0),
                nombre = cursor.getString(1),
                apellido = cursor.getString(2),
                fechaNacimiento = LocalDate.parse(cursor.getString(3)),
                salario = cursor.getDouble(4),
                esGerente = cursor.getInt(5) == 1,
                departamento = leerDepartamento(cursor.getInt(6))
            )
            cursor.close()
            db.close()
            empleado
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun actualizarEmpleado(empleado: BEmpleado): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", empleado.nombre)
            put("APELLIDO", empleado.apellido)
            put("FECHA_NACIMIENTO", empleado.fechaNacimiento.toString())
            put("SALARIO", empleado.salario)
            put("ES_GERENTE", if (empleado.esGerente) 1 else 0)
            put("DEPARTAMENTO_ID", empleado.departamento?.id)
        }
        val resultado = db.update("EMPLEADO", valores, "ID=?", arrayOf(empleado.id.toString()))
        db.close()
        return resultado > 0
    }

    fun eliminarEmpleado(id: Int): Boolean {
        val db = writableDatabase
        val resultado = db.delete("EMPLEADO", "ID=?", arrayOf(id.toString()))
        db.close()
        return resultado > 0
    }

    fun leerTodosLosDepartamentos(): ArrayList<BDepartamento> {
        val departamentos = ArrayList<BDepartamento>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM DEPARTAMENTO", null)

        if (cursor.moveToFirst()) {
            do {
                val departamento = BDepartamento(
                    id = cursor.getInt(0),
                    nombre = cursor.getString(1),
                    ubicacion = cursor.getString(2),
                    fechaCreacion = LocalDate.parse(cursor.getString(3)),
                    estaActivo = cursor.getInt(4) == 1,
                    presupuesto = cursor.getDouble(5)
                )
                departamentos.add(departamento)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return departamentos
    }

    fun leerTodosLosEmpleados(): ArrayList<BEmpleado> {
        val empleados = ArrayList<BEmpleado>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM EMPLEADO", null)

        if (cursor.moveToFirst()) {
            do {
                val empleado = BEmpleado(
                    id = cursor.getInt(0),
                    nombre = cursor.getString(1),
                    apellido = cursor.getString(2),
                    fechaNacimiento = LocalDate.parse(cursor.getString(3)),
                    salario = cursor.getDouble(4),
                    esGerente = cursor.getInt(5) == 1,
                    departamento = leerDepartamento(cursor.getInt(6))
                )
                empleados.add(empleado)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return empleados
    }

}
