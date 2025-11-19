package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Pedido (
    @SerializedName("id")
    override val id: Int,

    @SerializedName("proveedor_id")
    val proveedorId: Int,

    @SerializedName("fecha")
    val fecha: Date,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("nombre_proveedor")
    val nombreProveedor: String

    ) : CatalogItem {
    override val nombre: String
        get() = "Pedido $id"

    override val descripcion: String
        get() {
            val formatoFecha = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val fechaFormateada = fecha?.let { formatoFecha.format(it) } ?: "Fecha no disponible"

            return "Proveedor: $nombreProveedor \nFecha: $fechaFormateada \nEstado: ${estado?.uppercase()}"
        }
}