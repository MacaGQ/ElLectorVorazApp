package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class Articulo_Escolar(
    @SerializedName("id")
    override val id: Int,

    @SerializedName("nombre")
    override val nombre: String,

    @SerializedName("marca")
    val marca: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("proveedor_id")
    val proveedorId: Int,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("seccion")
    val seccion: String,

    @SerializedName("codigo")
    val codigo: String

) : CatalogItem {
    override val descripcion: String
        get() = marca
}

fun Articulo_Escolar.toMap(): Map<String, Any> {
    return mapOf(
        "nombre" to nombre,
        "marca" to marca,
        "precio" to precio,
        "stock" to stock,
        "seccion" to seccion,
        "codigo" to codigo
    )
}