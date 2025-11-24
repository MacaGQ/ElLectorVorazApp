package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class RevistaRequest (
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("edicion")
    val edicion: String,

    @SerializedName("numero")
    val numero: Int,

    @SerializedName("issn")
    val issn: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("proveedor_id")
    val proveedorId: Int
)