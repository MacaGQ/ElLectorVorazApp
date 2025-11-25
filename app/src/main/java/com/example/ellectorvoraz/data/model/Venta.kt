package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Venta (
    @SerializedName ("id")
    override val id: Int,

    @SerializedName("usuario_id")
    val usuarioId: Int,

    @SerializedName("fecha")
    val fecha: Date,

    @SerializedName("total")
    val total: Double
) : CatalogItem {
    override val nombre: String
        get() = "Venta #$id"

    override val descripcion: String
        get() {
            val formatoFecha = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
            val fechaFormateada = formatoFecha.format(fecha)
            return "Fecha: $fechaFormateada\nTotal: $total"
        }
}