package com.example.cruddepartameotnempleadogvlj

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class BEmpleado(
    var id: Int?,
    var nombre: String,
    var apellido: String,
    var fechaNacimiento: LocalDate,
    var salario: Double,
    var esGerente: Boolean,
    var departamento: BDepartamento?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        LocalDate.parse(parcel.readString()),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(BDepartamento::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: -1)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(fechaNacimiento.toString())
        parcel.writeDouble(salario)
        parcel.writeByte(if (esGerente) 1 else 0)
        parcel.writeParcelable(departamento, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEmpleado> {
        override fun createFromParcel(parcel: Parcel): BEmpleado {
            return BEmpleado(parcel)
        }

        override fun newArray(size: Int): Array<BEmpleado?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$nombre $apellido"
    }
}
