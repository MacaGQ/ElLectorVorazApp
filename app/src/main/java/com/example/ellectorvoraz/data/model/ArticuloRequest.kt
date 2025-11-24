package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class ArticuloRequest (
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("marca")
    val marca: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("seccion")
    val seccion: String,

    @SerializedName("codigo")
    val codigo: String,

    @SerializedName("proveedor_id")
    val proveedorId: Int
)