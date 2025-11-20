package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class Revista (
    @SerializedName ("id")
    override val id: Int,

    @SerializedName ("nombre")
    val nombreRevista: String,

    @SerializedName ("numero")
    val numero: Int,

    @SerializedName ("categoria")
    val categoria: String,

    @SerializedName ("precio")
    val precio : Double,

    @SerializedName ("proveedor_id")
    val proveedorId : Int,

    @SerializedName ("stock")
    val stock : Int,

    @SerializedName ("issn")
    val issn : String,

    @SerializedName ("edicion")
    val edicion : String,

) : CatalogItem {
    override val nombre: String get() = nombreRevista
    override val descripcion: String get() = "Edición: #$numero"
}