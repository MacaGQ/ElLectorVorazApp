package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class Proveedor (
    @SerializedName("id")
    override val id: Int,

    @SerializedName("nombre")
    val nombreProveedor: String,

    @SerializedName("telefono")
    val telefono: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("categoria")
    val categoria: String

) : CatalogItem {
    override val nombre: String
        get() = this.nombreProveedor
    override val descripcion: String
        get() = this.categoria
}