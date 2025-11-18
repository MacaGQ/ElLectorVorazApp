package com.example.ellectorvoraz.data.model

data class Libro (
    override val id: Int,
    val titulo: String,
    val autor: String,
    val isbn: String,
    val precio: Double,
    val editorial: String,
    val genero: String,
    val seccion: String,
    val id_proveedor: Int,
    val stock: Int
) : CatalogItem {
    override val nombre: String get() = this.titulo
    override val descripcion: String get() = this.autor
}