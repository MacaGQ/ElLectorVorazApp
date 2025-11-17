package com.example.ellectorvoraz.data

// Información que tiene que tener un item de cualquier catalogo
interface CatalogItem {
    val id: Int
    val name: String
    val picURL: String
    val description: String // Otro dato que puede cambiar en cada catalogo

}

//  Data classes que representan los items de cada catalogo

data class Libro(
    override val id: Int,
    override val name: String,
    override val picURL: String,
    val author: String,
) : CatalogItem {
    override val description: String
        get() = author
}

data class Revista(
    override val id: Int,
    override val name: String,
    override val picURL: String,
    val number: Int
) : CatalogItem {
    override val description: String
        get() = "Edición: #$number"
}

data class Articulo(
    override val id: Int,
    override val name: String,
    override val picURL: String,
    val marca: String,
) : CatalogItem {
    override val description: String
        get() = marca
}


