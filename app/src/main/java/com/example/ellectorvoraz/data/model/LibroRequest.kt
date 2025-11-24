package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class LibroRequest (
    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("autor")
    val autor: String,

    @SerializedName("editorial")
    val editorial: String,

    @SerializedName("isbn")
    val isbn: String,

    @SerializedName("genero")
    val genero: String,

    @SerializedName("seccion")
    val seccion: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("proveedor_id")
    val proveedorId: Int,

    @SerializedName ("stock")
    val stock: Int
)