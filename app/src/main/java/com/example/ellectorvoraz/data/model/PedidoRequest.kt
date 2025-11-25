package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class PedidoRequest(
    @SerializedName("proveedor_id")
    val proveedorId: Int,

    @SerializedName("estado")
    val estado: String = "pendiente",

    @SerializedName("detalle")
    val detalle: List<DetallePedidoRequest>
)
