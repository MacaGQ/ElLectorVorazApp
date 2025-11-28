package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class VentaRequest(
    @SerializedName("total")
    val total: Double,

    @SerializedName("detalle")
    val detalle: List<DetalleVentaRequest>

)
