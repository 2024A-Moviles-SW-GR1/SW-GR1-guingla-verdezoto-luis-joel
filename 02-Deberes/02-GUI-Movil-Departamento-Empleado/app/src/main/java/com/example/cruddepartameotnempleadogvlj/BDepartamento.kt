package com.example.cruddepartameotnempleadogvlj

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

class BDepartamento(
    var id: Int?,
    var nombre: String,
    var ubicacion: String,
    var fechaCreacion: LocalDate,
    var estaActivo: Boolean,
    var presupuesto: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        LocalDate.parse(parcel.readString()),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: -1)
        parcel.writeString(nombre)
        parcel.writeString(ubicacion)
        parcel.writeString(fechaCreacion.toString())
        parcel.writeByte(if (estaActivo) 1 else 0)
        parcel.writeDouble(presupuesto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BDepartamento> {
        override fun createFromParcel(parcel: Parcel): BDepartamento {
            return BDepartamento(parcel)
        }

        override fun newArray(size: Int): Array<BDepartamento?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return nombre
    }
}
