package com.example.ellectorvoraz

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ellectorvoraz.data.network.RetrofitClient
import kotlinx.coroutines.launch

class P21_PantallaCatalogoReutilizable : BaseActivity() {
    companion object {
        const val EXTRA_CATALOG_TYPE = "EXTRA_CATALOG_TYPE"
    }

    private lateinit var catalogAdapter: CatalogAdapter

    // Defaultea a catalogo de libros
    private var catalogType: String = "LIBROS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p21_catalogo_reutilizable)

        // Datos recibidos del intent en la pantalla anterior
        catalogType = intent.getStringExtra(EXTRA_CATALOG_TYPE) ?: "LIBROS"

        // Definicion del titulo de la pantalla
        val catalogTitle = when(catalogType) {
            "LIBROS" -> "CATÁLOGO DE LIBROS"
            "REVISTAS" -> "CATÁLOGO DE REVISTAS"
            "ARTICULOS" -> "CATÁLOGO DE ARTÍCULOS"
            else -> "CATÁLOGO"
        }

        // Inicializacion de la barra de superior
        setupTopBar(catalogTitle)

        // Carga de los datos anteriores en la UI
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.catalog_recycler_view)
        // Se inicializa el adaptador con una lista vacia. Se llena con la llamada a la API
        catalogAdapter = CatalogAdapter(emptyList())
        recyclerView.adapter = catalogAdapter

        // Cargar todos los datos
        performSearch("")
    }

    // Busca los datos en la BBDD y los muestra
    // Si la query esta vacia, muestra todos los datos
    // Si la query no esta vacia, muestra los datos que coincidan con la query
    // El catalogo se arma en CatalogAdapter
    private fun performSearch(query:String) {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P21_PantallaCatalogoReutilizable)

                val response = when(catalogType) {
                    "LIBROS" -> api.getLibros(query)
                    "REVISTAS" -> api.getRevistas(query)
                    "ARTICULOS" -> api.getArticulos(query)
                    else -> {
                        Log.e("API_CALL", "Catalogo desconocido: $catalogType")
                        null
                    }
                }

                if (response != null && response.isSuccessful) {
                    response.body()?.let { items ->
                        catalogAdapter.updateItems(items)
                    }
                } else {
                    Log.e("API_CALL", "Error en la llamada a la API: ${response?.code()} - ${response?.message()}")
                    Toast.makeText(this@P21_PantallaCatalogoReutilizable, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("API_CALL", "Exception: ${e.message}")
                Toast.makeText(this@P21_PantallaCatalogoReutilizable, "Error de conexion", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // Funcionalidad de búsqueda
    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.catalogo_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@P21_PantallaCatalogoReutilizable, "Buscando: $query", Toast.LENGTH_SHORT).show()
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
