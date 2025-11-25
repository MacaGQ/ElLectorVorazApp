package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class DetalleVenta(
    @SerializedName("id")
    val id: Int,

    @SerializedName("venta_id")
    val ventaId: Int,

    @SerializedName("tipo_producto")
    val tipoProducto: String,

    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("precio_unitario")
    val precioUnitario: Double,

    @SerializedName("subtotal")
    val subtotal: Double,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("nombre_producto")
    val nombreProducto: String
)
