package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class Libro (
    @SerializedName("id")
    override val id: Int,

    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("autor")
    val autor: String,

    @SerializedName("isbn")
    val isbn: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("editorial")
    val editorial: String,

    @SerializedName("genero")
    val genero: String,

    @SerializedName("seccion")
    val seccion: String,

    @SerializedName("proveedor_id")
    val proveedorId: Int,
    val stock: Int
) : CatalogItem {
    override val nombre: String get() = this.titulo
    override val descripcion: String get() = this.autor
}

fun Libro.toMap(): Map<String, Any> {
    return mapOf(
        "titulo" to titulo,
        "autor" to autor,
        "isbn" to isbn,
        "precio" to precio,
        "editorial" to editorial,
        "genero" to genero,
        "seccion" to seccion,
        "stock" to stock,
    )
}