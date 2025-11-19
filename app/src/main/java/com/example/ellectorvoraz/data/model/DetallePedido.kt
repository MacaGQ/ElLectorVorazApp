package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class DetallePedido (
    @SerializedName("id")
    val id : Int,

    @SerializedName("id_pedido")
    val idPedido : Int,

    @SerializedName("tipo_producto")
    val tipoProducto : String,

    @SerializedName("id_producto")
    val idProducto : Int,

    @SerializedName("cantidad")
    val cantidad : Int,

    @SerializedName("precio_unitario")
    val precioUnitario : Double,

    @SerializedName("categoria")
    val categoria : String,

    @SerializedName("nombre_producto")
    val nombreProducto: String
)