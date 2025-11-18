package com.example.ellectorvoraz.data.model

data class Articulo_Escolar(
    override val id: Int,
    override val nombre: String,
    val marca: String,
    val precio: Double,
    val id_proveedor: Int,
    val stock: Int,
    val seccion: String,
    val codigo: String
) : CatalogItem {
    override val descripcion: String
        get() = marca
}
