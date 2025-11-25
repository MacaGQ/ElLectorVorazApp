package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class DetalleVentaRequest(
    @SerializedName("tipo_producto")
    val tipoProducto: String,

    @SerializedName("producto_id")
    val productoId: Int,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("precio_unitario")
    val precioUnitario: Double,

    @SerializedName("subtotal")
    val subtotal: Double
)
