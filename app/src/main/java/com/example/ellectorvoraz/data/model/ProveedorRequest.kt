package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class ProveedorRequest(
    @SerializedName ("nombre")
    val nombre: String,

    @SerializedName ("telefono")
    val telefono: String,

    @SerializedName ("email")
    val email: String,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName ("categoria")
    val categoria: String
)
