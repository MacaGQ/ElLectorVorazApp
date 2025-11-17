package com.example.ellectorvoraz

import android.os.Bundle
import android.widget.Toast
import com.example.ellectorvoraz.data.PlaceholderDB

class P21_PantallaCatalogoLibros : BaseActivity() {
    companion object {
        const val EXTRA_CATALOG_TYPE = "EXTRA_CATALOG_TYPE"
    }

    private lateinit var catalogAdapter: CatalogAdapter

    // Defaultea a catalogo de libros
    private var catalogType: String = "LIBROS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p21_catalogo_libros)

        // Datos recibidos del intent en la pantalla anterior
        catalogType = intent.getStringExtra(EXTRA_CATALOG_TYPE) ?: "LIBROS"

        // Definicion del titulo de la pantalla
        val catalogTitle = when(catalogType) {
            "LIBROS" -> "CATÁLOGO DE LIBROS"
            "REVISTAS" -> "CATÁLOGO DE REVISTAS"
            "ARTICULOS" -> "CATÁLOGO DE ARTÍCULOS"
            else -> "CATÁLOGO"
        }

        // Inicializacion de las barra de superior
        setupTopBar(catalogTitle)

        // Carga de los datos anteriores en la UI
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.catalog_recycler_view)
        catalogAdapter = CatalogAdapter(emptyList())
        recyclerView.adapter = catalogAdapter

        // Cargar los datos
        performSearch("")

    }

    private fun performSearch(query:String) {
        val results = PlaceholderDB.searchCatalogItems(catalogType, query)
        catalogAdapter.updateItems(results)
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.catalogo_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@P21_PantallaCatalogoLibros, "Buscando: $query", Toast.LENGTH_SHORT).show()
                performSearch(query ?: "")
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}
