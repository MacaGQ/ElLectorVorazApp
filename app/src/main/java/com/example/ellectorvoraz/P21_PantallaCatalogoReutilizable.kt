package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.ellectorvoraz.adapters.CatalogAdapter
import com.example.ellectorvoraz.data.model.Articulo_Escolar
import com.example.ellectorvoraz.data.model.Libro
import com.example.ellectorvoraz.data.model.Revista
import com.example.ellectorvoraz.data.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class P21_PantallaCatalogoReutilizable : BaseActivity() {
    companion object {
        // Tipo de catalogo que se va a cargar
        const val EXTRA_CATALOG_TYPE = "EXTRA_CATALOG_TYPE"

        // Modo de operación:
        // Modo Navegacion - Funciona como catalogo normal, al hacer click muestra los detalles
        // Modo Seleccion - Funciona como selector (de proveedores) para los formularios de registro
        const val EXTRA_OPERATION_MODE = "EXTRA_OPERATION_MODE"
        const val MODE_NAVIGATION = "MODE_NAVIGATION"
        const val MODE_SELECTION = "MODE_SELECTION"

        // Se guardan el ID y Nombre del proveedor para mandar al formulario de registro
        const val RESULT_SELECTED_ID = "RESULT_SELECTED_ID"
        const val RESULT_SELECTED_NAME = "RESULT_SELECTED_NAME"

        // Se guardan el tupo de producto y el precio para las transacciones
        const val RESULT_PRODUCT_TYPE = "RESULT_PRODUCT_TYPE"
        const val RESULT_PRODUCT_PRICE = "RESULT_PRODUCT_PRICE"
    }

    private lateinit var catalogAdapter: CatalogAdapter
    private var operationMode: String = MODE_NAVIGATION
    private var criterioBusqueda: String = "search"
    private val criteriosDisponibles = mapOf(
        "LIBROS" to listOf("Todos", "Titulo", "Autor", "Editorial", "ISBN", "Genero"),
        "REVISTAS" to listOf("Todos", "Nombre", "Categoria", "ISSN"),
        "ARTICULOS" to listOf("Todos", "Nombre", "Marca", "Seccion", "Codigo"),
        "PEDIDOS" to listOf("Todos", "Estado", "Proveedor", "Tipo de Producto" ,"Categoria"),
        "PROVEEDORES" to listOf("Todos", "Nombre", "Categoria")
    )
    private var searchJob: Job? = null

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    // Defaultea a catalogo de libros
    private var catalogType: String = "LIBROS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p21_catalogo_reutilizable)

        // Datos recibidos del intent en la pantalla anterior
        catalogType = intent.getStringExtra(EXTRA_CATALOG_TYPE) ?: "LIBROS"
        operationMode = intent.getStringExtra(EXTRA_OPERATION_MODE) ?: MODE_NAVIGATION


        // Definicion del titulo de la pantalla
        val catalogTitle = when(catalogType) {
            "LIBROS" -> "CATÁLOGO DE LIBROS"
            "REVISTAS" -> "CATÁLOGO DE REVISTAS"
            "ARTICULOS" -> "CATÁLOGO DE ARTÍCULOS"
            "PEDIDOS" -> "LISTADO DE PEDIDOS"
            "PROVEEDORES" -> "LISTADO DE PROVEEDORES"
            "VENTAS" -> "LISTADO DE VENTAS"
            else -> "CATÁLOGO"
        }

        // Inicializacion de la barra de superior
        setupTopBar(catalogTitle)

        // Carga de los datos anteriores en la UI
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.catalog_recycler_view)


        catalogAdapter = CatalogAdapter { clickedItem ->
            when (operationMode) {
                MODE_NAVIGATION -> {
                    val intent = Intent(this, P25_SeleccionElemento::class.java)
                    intent.putExtra("EXTRA_ITEM_ID", clickedItem.id)
                    intent.putExtra("EXTRA_CATALOG_TYPE", catalogType)
                    startActivity(intent)
                }
                MODE_SELECTION -> {
                    val resultIntent = Intent()
                    resultIntent.putExtra(RESULT_SELECTED_ID, clickedItem.id)
                    resultIntent.putExtra(RESULT_SELECTED_NAME, clickedItem.nombre)

                    when (clickedItem) {
                        is Libro -> {
                            resultIntent.putExtra(RESULT_PRODUCT_TYPE, "libro")
                            resultIntent.putExtra(RESULT_PRODUCT_PRICE, clickedItem.precio)
                        }
                        is Revista -> {
                            resultIntent.putExtra(RESULT_PRODUCT_TYPE, "revista")
                            resultIntent.putExtra(RESULT_PRODUCT_PRICE, clickedItem.precio)
                        }
                        is Articulo_Escolar -> {
                            resultIntent.putExtra(RESULT_PRODUCT_TYPE, "articulo_escolar")
                            resultIntent.putExtra(RESULT_PRODUCT_PRICE, clickedItem.precio)
                        }
                    }

                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
        }

        recyclerView.adapter = catalogAdapter

        // Cargar todos los datos
        performSearch("")
    }

    // Busca los datos en la BBDD y los muestra
    // Si la query esta vacia, muestra todos los datos
    // Si la query no esta vacia, muestra los datos que coincidan con la query (global o por filtros)
    // El catalogo se arma en CatalogAdapter
    private fun performSearch(query:String) {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P21_PantallaCatalogoReutilizable)

                val params = mutableMapOf<String, String>()
                if (query.isNotBlank()) {
                    val claveApi = when (criterioBusqueda) {
                        "Todos" -> "search"
                        "Proveedor" -> "nombre_proveedor"
                        "Tipo de Producto" -> "tipo_producto"
                        else -> criterioBusqueda.lowercase()
                    }
                    params[claveApi.lowercase()] = query
                }

                val response = when(catalogType) {
                    "LIBROS" -> api.getLibros(params)
                    "REVISTAS" -> api.getRevistas(params)
                    "ARTICULOS" -> api.getArticulos(params)
                    "PEDIDOS" -> api.getPedidos(params)
                    "PROVEEDORES" -> api.getProveedores(params)
                    "VENTAS" -> api.getVentas(params)
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
        searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchJob?.cancel()
                performSearch(query ?: "")
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(500L)
                    performSearch(newText ?: "")
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        val filtros = criteriosDisponibles[catalogType] ?: return
        val checkedItem = filtros.indexOf(criterioBusqueda).let { if (it == -1) 0 else it}

        AlertDialog.Builder(this)
            .setTitle("Seleccionar Criterio")
            .setSingleChoiceItems(filtros.toTypedArray(), checkedItem) { dialog, which ->
                criterioBusqueda = filtros[which]
                Toast.makeText(
                    this,
                    "Criterio de busqueda cambiado a $criterioBusqueda",
                    Toast.LENGTH_SHORT
                ).show()

                updateSearchHint()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateSearchHint() {
        if (::searchView.isInitialized) {
            searchView.queryHint = "Buscar por: $criterioBusqueda"
        }
    }
}
