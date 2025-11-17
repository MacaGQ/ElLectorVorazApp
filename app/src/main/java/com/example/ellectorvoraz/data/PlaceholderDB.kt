package com.example.ellectorvoraz.data

object PlaceholderDB {

    // Libros
    private val fakeBooks = listOf(
        Libro(
            id = 1,
            name = "El señor de los anillos",
            author = "J.R.R. Tolkien",
            picURL = "RUTA_IMAGEN",),
        Libro(
            id = 2,
            name = "Harry Potter y la piedra filosofal",
            author = "J.K. Rowling",
            picURL = "RUTA_IMAGEN"),
        Libro(
            id = 3,
            name = "1984",
            author = "George Orwell",
            picURL = "RUTA_IMAGEN"),
        Libro(
            id = 4,
            name = "Cien años de soledad",
            author = "Gabriel García Márquez",
            picURL = "RUTA_IMAGEN") ,
        Libro(
            id = 5,
            name = "El principito",
            author = "Antoine de Saint-Exupéry",
            picURL = "RUTA_IMAGEN")
    )

    // Revistas
    private val fakeMagazines = listOf(
        Revista(id = 1, name = "El Mundo", number = 145, picURL = "RUTA_IMAGEN"),
        Revista(id = 2, name = "La Nación", number = 123, picURL = "RUTA_IMAGEN"),
        Revista(id = 3, name = "El Comercio", number = 111, picURL = "RUTA_IMAGEN"),
        Revista(id = 4, name = "El País", number = 101, picURL = "RUTA_IMAGEN"),
        Revista(id = 5, name = "El Periódico", number = 99, picURL = "RUTA_IMAGEN")
    )

    // Articulos de libreria
    private val fakeArticles = listOf(
        Articulo(id = 1, name = "Tijera", marca= "Mapped", picURL = "RUTA_IMAGEN"),
        Articulo(id = 2, name = "Lápiz", marca= "Faber Castel", picURL = "RUTA_IMAGEN"),
        Articulo(id = 3, name = "Cuaderno", marca= "Gloria", picURL = "RUTA_IMAGEN"),
        Articulo(id = 4, name = "Regla", marca= "Mapped", picURL = "RUTA_IMAGEN"),
        Articulo(id = 5, name = "Lapicera", marca= "Bic", picURL = "RUTA_IMAGEN")
    )

    // Función que simula busqueda en la BBDD
    fun searchCatalogItems(catalogType: String,query: String): List<CatalogItem> {
        val lowerCaseQuery = query.lowercase().trim()

        return when (catalogType) {
            "LIBROS" -> {
                if(lowerCaseQuery.isBlank()) fakeBooks else fakeBooks.filter {
                    it.name.lowercase().contains(lowerCaseQuery) || it.author.lowercase().contains(lowerCaseQuery)
                }
            }
            "REVISTAS" -> {
                if(lowerCaseQuery.isBlank()) fakeMagazines else fakeMagazines.filter {
                    it.name.lowercase().contains(lowerCaseQuery)
                }
            }
            "ARTICULOS" -> {
                if(lowerCaseQuery.isBlank()) fakeArticles else fakeArticles.filter {
                    it.name.lowercase().contains(lowerCaseQuery) || it.marca.lowercase().contains(lowerCaseQuery)
                }
            }
            else -> emptyList()
        }
    }
}