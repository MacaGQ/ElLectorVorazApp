package com.example.ellectorvoraz.data.model

data class Revista (
    override val id: Int,
    override val nombre: String,
    val numero: Int,
    val categoria: String,
    val precio : Double,
    val id_proveedor : Int,
    val stock : Int,
    val issn : String,
    val edicion : String,
) : CatalogItem {
    override val descripcion: String get() = "Edición: #$numero"
}