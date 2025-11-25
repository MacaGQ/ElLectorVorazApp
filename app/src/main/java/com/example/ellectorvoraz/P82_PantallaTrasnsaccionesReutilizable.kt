package com.example.ellectorvoraz

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.TransactionAdapter
import com.example.ellectorvoraz.data.model.DetallePedidoRequest
import com.example.ellectorvoraz.data.model.DetalleVentaRequest
import com.example.ellectorvoraz.data.model.TransactionItem
import com.example.ellectorvoraz.data.network.RetrofitClient
import com.example.ellectorvoraz.data.repository.CreationRepository
import com.example.ellectorvoraz.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class P82_PantallaTrasnsaccionesReutilizable : BaseActivity() {

    companion object {
        const val EXTRA_TRANSACTION_TYPE = "EXTRA_TRANSACTION_TYPE"
        const val TYPE_VENTA = "VENTA"
        const val TYPE_PEDIDO = "PEDIDO"
    }

    // Variables de estado
    private lateinit var transactionType : String
    private var mainEntityId: Int? = null
    private var itemsEnCarrito = mutableListOf<TransactionItem>()

    private lateinit var creationRepository: CreationRepository
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var valorPrincipalTextView: TextView
    private lateinit var valorTotalTextView: TextView

    private val lanzadorSeleccionEntidadPrincipal = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            mainEntityId =
                data?.getIntExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_ID, -1)
            val nombreEntidad =
                data?.getStringExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_NAME)
            valorPrincipalTextView.text = nombreEntidad ?: "Seleccione..."
        }
    }

    private val lanzadorSeleccionProducto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val productoId =
                data?.getIntExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_ID, -1)
            val nombreProducto =
                data?.getStringExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_NAME) ?: "N/A"
            val tipoProducto =
                data?.getStringExtra(P21_PantallaCatalogoReutilizable.RESULT_PRODUCT_TYPE) ?: ""
            val precio =
                data?.getDoubleExtra(P21_PantallaCatalogoReutilizable.RESULT_PRODUCT_PRICE, 0.0)
                    ?: 0.0

            if (productoId != null && productoId != -1) {
                val itemExistente = itemsEnCarrito.find { it.productoId == productoId && it.tipoProducto == tipoProducto }
                if (itemExistente != null) {
                    itemExistente.cantidad++
                    itemExistente.subtotal = itemExistente.cantidad * itemExistente.precioUnitario
                } else {
                    val precioInicialUnitario = if( transactionType == TYPE_VENTA) precio else 0.0
                    val nuevoItem = TransactionItem(
                        productoId,
                        tipoProducto,
                        nombreProducto,
                        1,
                        precioInicialUnitario,
                        precioInicialUnitario)
                    itemsEnCarrito.add(nuevoItem)
                }

                transactionAdapter.notifyDataSetChanged()
                actualizarTotal()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p82_pantalla_transacciones_reutilizable)

        val api = RetrofitClient.getInstance(this)
        creationRepository = CreationRepository(api)

        transactionType = intent.getStringExtra(EXTRA_TRANSACTION_TYPE) ?: ""

        if(transactionType.isEmpty()) {
            Toast.makeText(this, "Error: Tipo de transacción no especificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        valorPrincipalTextView = findViewById(R.id.valor_principal)
        valorTotalTextView = findViewById(R.id.valor_total)

        val btnSeleccionar: Button = findViewById(R.id.btn_seleccionar)
        val btnFinalizar: Button = findViewById(R.id.btn_finalizar)

        when (transactionType) {
            TYPE_PEDIDO -> {
                setupTopBar("Nuevo Pedido")
                btnSeleccionar.text = "Proveedor"
                btnFinalizar.text = "Finalizar Pedido"
                valorPrincipalTextView.text = "Seleccione un Proveedor"
            }

            TYPE_VENTA -> {
                setupTopBar("Nueva Venta")
                findViewById<View>(R.id.main_entity_section).visibility = View.GONE
                btnFinalizar.text = "Finalizar Venta"
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.transaccion_recycler_view)
        transactionAdapter = TransactionAdapter(
            itemsEnCarrito,
            { actualizarTotal() },
            transactionType == TYPE_PEDIDO
        )
        recyclerView.adapter = transactionAdapter

        btnSeleccionar.setOnClickListener {
            if (transactionType == TYPE_PEDIDO) {
                val intent = Intent(this, P21_PantallaCatalogoReutilizable::class.java)
                intent.putExtra(P21_PantallaCatalogoReutilizable.EXTRA_CATALOG_TYPE, "PROVEEDORES")
                intent.putExtra(
                    P21_PantallaCatalogoReutilizable.EXTRA_OPERATION_MODE,
                    P21_PantallaCatalogoReutilizable.MODE_SELECTION
                )
                lanzadorSeleccionEntidadPrincipal.launch(intent)
            }
        }

        findViewById<Button>(R.id.btn_agregar_producto).setOnClickListener {
            val productTypes = arrayOf("Libro", "Revista", "Artículo Escolar")
            AlertDialog.Builder(this)
                .setTitle("Tipo de Producto")
                .setItems(productTypes) { dialog, which ->
                    val selectedCatalogType = when (which) {
                        0 -> "LIBROS"
                        1 -> "REVISTAS"
                        2 -> "ARTICULOS"
                        else -> "LIBROS"
                    }

                    val intent = Intent(this, P21_PantallaCatalogoReutilizable::class.java)
                    intent.putExtra(
                        P21_PantallaCatalogoReutilizable.EXTRA_CATALOG_TYPE,
                        selectedCatalogType
                    )
                    intent.putExtra(
                        P21_PantallaCatalogoReutilizable.EXTRA_OPERATION_MODE,
                        P21_PantallaCatalogoReutilizable.MODE_SELECTION
                    )
                    lanzadorSeleccionProducto.launch(intent)
                    dialog.dismiss()
                }
                .show()
        }

        btnFinalizar.setOnClickListener {
            handleSubmit()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (itemsEnCarrito.isNotEmpty()) {
                    mostrarAlertaAlSalir()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)

        setupBottomNav()

    }

    private fun actualizarTotal() {
        val total = itemsEnCarrito.sumOf { it.subtotal}
        valorTotalTextView.text = "$${String.format("%.2f", total)}"
    }

    private fun handleSubmit() {
        lifecycleScope.launch {
            if((transactionType == TYPE_PEDIDO && mainEntityId == null)) {
                Toast.makeText(this@P82_PantallaTrasnsaccionesReutilizable, "Debe seleccionar un proveedor", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (itemsEnCarrito.isEmpty()) {
                Toast.makeText(this@P82_PantallaTrasnsaccionesReutilizable, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val data = emptyMap<String, String>()
            val extraData = mutableMapOf<String, Any?>()

            when (transactionType) {
                TYPE_PEDIDO -> {
                    extraData["proveedorId"] = mainEntityId
                    val detalle = itemsEnCarrito.map {
                        DetallePedidoRequest(
                            tipoProducto = it.tipoProducto,
                            productoId = it.productoId,
                            cantidad = it.cantidad,
                            precioUnitario = it.precioUnitario
                        )
                    }
                    extraData["detalle"] = detalle
                    extraData["estado"] = "pendiente"
                }

                TYPE_VENTA -> {
                    val vendedorId =
                        SharedPreferencesManager.getUserId(this@P82_PantallaTrasnsaccionesReutilizable)
                    if (vendedorId == -1) {
                        Toast.makeText(
                            this@P82_PantallaTrasnsaccionesReutilizable,
                            "Error: No se pudo obtener el ID del vendedor",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    extraData["usuarioId"] = vendedorId

                    val detalleVenta = itemsEnCarrito.map {
                        DetalleVentaRequest(
                            tipoProducto = it.tipoProducto,
                            productoId = it.productoId,
                            cantidad = it.cantidad,
                            precioUnitario = it.precioUnitario,
                            subtotal = it.subtotal
                        )
                    }

                    extraData["detalle"] = detalleVenta
                    extraData["total"] = itemsEnCarrito.sumOf { it.subtotal }
                }
            }

            try {
                val response = creationRepository.createItem(transactionType, data, extraData)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@P82_PantallaTrasnsaccionesReutilizable,
                        "Transacción exitosa",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@P82_PantallaTrasnsaccionesReutilizable,
                        "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("TRANSACTION_SUBMIT_ERROR", "Fallo al guardar: ${e.message}", e)
                Toast.makeText(
                    this@P82_PantallaTrasnsaccionesReutilizable,
                    "Error al guardar: ${e.message}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun mostrarAlertaAlSalir() {
        AlertDialog.Builder(this)
            .setTitle("Descartar cambios")
            .setMessage("¿Desea descartar los cambios? Se perderá el progreso realizado")
            .setPositiveButton("Sí") { _, _ ->
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

}