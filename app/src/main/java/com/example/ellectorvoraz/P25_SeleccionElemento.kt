package com.example.ellectorvoraz

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.DetalleAdapter
import com.example.ellectorvoraz.data.model.Articulo_Escolar
import com.example.ellectorvoraz.data.model.DetalleCaracteristica
import com.example.ellectorvoraz.data.model.Libro
import com.example.ellectorvoraz.data.model.Revista
import com.example.ellectorvoraz.data.network.RetrofitClient
import kotlinx.coroutines.launch

class P25_SeleccionElemento : BaseActivity() {

    private lateinit var tituloTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var detalleRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p25_seleccion_elemento)
        setupTopBar("Detalle del Producto")

        // Vistas Principales
        tituloTextView = findViewById(R.id.detalle_txt_titulo)
        descripcionTextView = findViewById(R.id.detalle_txt_descripcion)
        detalleRecyclerView = findViewById(R.id.detalle_recycler)

        // Recibir datos del Intent
        val itemId = intent.getIntExtra("EXTRA_ITEM_ID", -1)
        val catalogType = intent.getStringExtra("EXTRA_CATALOG_TYPE")

        if (itemId == -1 || catalogType == null) {
            Toast.makeText(this, "Error: No se pudo cargar el item", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Llamar a la API
        fetchItemDetails(itemId, catalogType)
    }

    private fun fetchItemDetails(id: Int, type: String) {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P25_SeleccionElemento)

                val item: Any? = when (type) {
                    "LIBROS" -> api.getLibroId(id).body()
                    "REVISTAS" -> api.getRevistaId(id).body()
                    "ARTICULOS" -> api.getArticuloId(id).body()
                    else -> null
                }

                if (item != null) {
                    updateUiWithItem(item)
                } else {
                    Toast.makeText(
                        this@P25_SeleccionElemento,
                        "No se encontraron detalles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("DETALLE_ERROR", "Fallo al obtener detalles del item", e)
                Toast.makeText(this@P25_SeleccionElemento, "Error al obtener detalles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUiWithItem(item: Any) {
        // Lista vacia que almacena las caracteristicas
        val caracteristicas = mutableListOf<DetalleCaracteristica>()

        when (item) {
            is Libro -> {
                // Campos principales
                tituloTextView.text = item.titulo
                descripcionTextView.text = item.autor

                // Caracteristicas especificos
                caracteristicas.add(DetalleCaracteristica("Precio", "$${item.precio}"))
                caracteristicas.add(DetalleCaracteristica("Stock Disponible", "${item.stock} unidades"))
                caracteristicas.add(DetalleCaracteristica("ISBN", item.isbn))
                caracteristicas.add(DetalleCaracteristica("Editorial", item.editorial))
                caracteristicas.add(DetalleCaracteristica("Género", item.genero))
                caracteristicas.add(DetalleCaracteristica("Seccion", item.seccion))
            }
            is Revista -> {
                // Campos principales
                tituloTextView.text = item.nombre
                descripcionTextView.text = "Numero: ${item.numero}"

                // Caracteristicas especificas
                caracteristicas.add(DetalleCaracteristica("Precio", "$${item.precio}"))
                caracteristicas.add(DetalleCaracteristica("Stock Disponible", "${item.stock} unidades"))
                caracteristicas.add(DetalleCaracteristica("Categoria", item.categoria))
                caracteristicas.add(DetalleCaracteristica("ISSN", item.issn))
                caracteristicas.add(DetalleCaracteristica("Edicion", item.edicion))
            }
            is Articulo_Escolar -> {
                // Campos principales
                tituloTextView.text = item.nombre
                descripcionTextView.text = item.marca

                // Caracteristicas especificas
                caracteristicas.add(DetalleCaracteristica("Precio", "$${item.precio}"))
                caracteristicas.add(DetalleCaracteristica("Stock Disponible", "${item.stock} unidades"))
                caracteristicas.add(DetalleCaracteristica("Seccion", item.seccion))
                caracteristicas.add(DetalleCaracteristica("Codigo", item.codigo))

            }
            else -> {
                Log.w("UPDATE_UI_WARN", "Se recibio un item desconocido: ${item.javaClass.simpleName}")
            }
        }
        detalleRecyclerView.adapter = DetalleAdapter(caracteristicas)
    }
}
