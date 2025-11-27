package com.example.ellectorvoraz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.DetalleAdapter
import com.example.ellectorvoraz.data.model.Articulo_Escolar
import com.example.ellectorvoraz.data.model.DetalleCaracteristica
import com.example.ellectorvoraz.data.model.DetallePedido
import com.example.ellectorvoraz.data.model.DetalleVenta
import com.example.ellectorvoraz.data.model.Libro
import com.example.ellectorvoraz.data.model.Pedido
import com.example.ellectorvoraz.data.model.Proveedor
import com.example.ellectorvoraz.data.model.Revista
import com.example.ellectorvoraz.data.model.Usuario
import com.example.ellectorvoraz.data.model.Venta
import com.example.ellectorvoraz.data.network.RetrofitClient
import com.example.ellectorvoraz.data.repository.CreationRepository
import com.example.ellectorvoraz.util.SharedPreferencesManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class P25_SeleccionElemento : BaseActivity() {

    private lateinit var tituloTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var detalleRecyclerView: RecyclerView

    private var currentItemId: Int = -1
    private var currentCatalogType: String? = null

    private var currentPedido: Pedido? = null
    private lateinit var creationRepository: CreationRepository

    private val editLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            fetchItemDetails(currentItemId, currentCatalogType!!)
            Toast.makeText(
                this,
                "Actualizacion exitosa",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p25_seleccion_elemento)
        setupTopBar("Detalle del Producto")

        setupBottomNav()

        // Vistas Principales
        tituloTextView = findViewById(R.id.detalle_txt_titulo)
        descripcionTextView = findViewById(R.id.detalle_txt_descripcion)
        detalleRecyclerView = findViewById(R.id.detalle_recycler)

        // Recibir datos del Intent
        val itemId = intent.getIntExtra("EXTRA_ITEM_ID", -1)
        val catalogType = intent.getStringExtra("EXTRA_CATALOG_TYPE")

        this.currentItemId = itemId
        this.currentCatalogType = catalogType

        val api = RetrofitClient.getInstance(this)
        creationRepository = CreationRepository(api)

        if (itemId == -1 || catalogType == null) {
            Toast.makeText(this, "Error: No se pudo cargar el item", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Llamar a la API
        fetchItemDetails(itemId, catalogType)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (currentCatalogType in listOf("LIBROS", "REVISTAS", "ARTICULOS", "PROVEEDORES", "PEDIDOS")) {
            menuInflater.inflate(R.menu.detalle_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                if (currentCatalogType == "PEDIDOS" && currentPedido != null) {
                    mostrarDialogoCambioEstado()
                } else {
                    mostrarDialogoAcciones()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchItemDetails(id: Int, type: String) {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P25_SeleccionElemento)

                if (type == "PERFIL_USUARIO") {
                    val usuario = SharedPreferencesManager.getUser(this@P25_SeleccionElemento)

                    if (usuario != null) {
                        try {
                            val rolDeferrer = async { api.getRolById(usuario.rolId) }
                            val rolResponse = rolDeferrer.await()
                            val rol = rolResponse.body()

                            updateUiWithItem(usuario, rol)
                        } catch (e: Exception) {
                            Log.e("PERFIL_ROL_ERROR", "Error al obtener el rol del usuario", e)
                            Toast.makeText(
                                this@P25_SeleccionElemento,
                                "Error al obtener el rol del usuario",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUiWithItem(usuario, null)
                        }
                    } else {
                            Toast.makeText(
                                this@P25_SeleccionElemento,
                                "No se encontraron datos del perfil",
                                Toast.LENGTH_SHORT
                            ).show ()
                    }
                } else if (type == "PEDIDOS") {
                    val pedidoDeferred = async { api.getPedidoId(id) }
                    val detallesDeferred = async { api.getDetallePedido(id) }

                    val pedidoResponse = pedidoDeferred.await()
                    val detallesResponse = detallesDeferred.await()

                    if (pedidoResponse.isSuccessful && detallesResponse.isSuccessful) {
                        val pedido = pedidoResponse.body()
                        val detalles = detallesResponse.body()

                        if (pedido != null && detalles != null) {
                            this@P25_SeleccionElemento.currentPedido = pedido
                            updateUiPorPedido(pedido, detalles)
                        } else {
                            Toast.makeText(
                                this@P25_SeleccionElemento,
                                "No se encontraron datos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@P25_SeleccionElemento,
                            "Error al obtener detalles del pedido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else if (type == "VENTAS") {
                    val ventaDeferred = async { api.getVentaId(id) }
                    val detallesDeferred = async { api.getDetalleVenta(id) }

                    val ventaResponse = ventaDeferred.await()
                    val detallesResponse = detallesDeferred.await()

                    if (ventaResponse.isSuccessful && detallesResponse.isSuccessful) {
                        val venta = ventaResponse.body()
                        val detalles = detallesResponse.body()

                        if (venta != null && detalles != null) {
                            updateUiPorVenta(venta, detalles)
                        } else {
                            Toast.makeText(this@P25_SeleccionElemento, "No se encontraron datos para la venta", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("VENTA_DETALLE_ERRROR", "Error al obtener detalles de la venta: ${ventaResponse.code()} - ${ventaResponse.message()}")
                        Toast.makeText(this@P25_SeleccionElemento, "Error al obtener detalles de la venta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val item: Any? = when (type) {
                        "LIBROS" -> api.getLibroId(id).body()
                        "REVISTAS" -> api.getRevistaId(id).body()
                        "ARTICULOS" -> api.getArticuloId(id).body()
                        "PROVEEDORES" -> api.getProveedorId(id).body()
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
                }

            } catch (e: Exception) {
                Log.e("DETALLE_ERROR", "Fallo al obtener detalles del item", e)
                Toast.makeText(this@P25_SeleccionElemento, "Error al obtener detalles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUiWithItem(item: Any, rol: com.example.ellectorvoraz.data.model.Rol? = null) {
        // Lista vacia que almacena las caracteristicas
        val caracteristicas = mutableListOf<DetalleCaracteristica>()

        when (item) {
            is Usuario -> {
                setupTopBar("Mi Perfil")
                val nombreRol = rol?.nombre
                tituloTextView.text = "Usuario: ${item.username}"
                descripcionTextView.text = "Rol: $nombreRol"
            }
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
            is Proveedor -> {
                // Campos principales
                tituloTextView.text = item.nombre
                descripcionTextView.text = item.descripcion

                // Caracteristicas especificas
                caracteristicas.add(DetalleCaracteristica("Email", item.email))
                caracteristicas.add(DetalleCaracteristica("Telefono", item.telefono))
                caracteristicas.add(DetalleCaracteristica("Direccion", item.direccion))
            }
            else -> {
                Log.w("UPDATE_UI_WARN", "Se recibio un item desconocido: ${item.javaClass.simpleName}")
            }
        }
        detalleRecyclerView.adapter = DetalleAdapter(caracteristicas)
    }

    private fun updateUiPorPedido(pedido: Pedido, detalles: List<DetallePedido>) {
        tituloTextView.text = "Detalle del Pedido N° ${pedido.id}"
        descripcionTextView.text = pedido.descripcion

        val caracteristicas = mutableListOf<DetalleCaracteristica>()

        detalles.forEach { detalle ->
            caracteristicas.add(
                DetalleCaracteristica(
                    etiqueta = detalle.nombreProducto,
                    valor = "Cantidad: ${detalle.cantidad} | Precio Unitario: ${detalle.precioUnitario}"
                )
            )
        }

        detalleRecyclerView.adapter = DetalleAdapter(caracteristicas)

    }

    private fun updateUiPorVenta(venta: Venta, detalles: List<DetalleVenta>) {
        setupTopBar("Detalle de Venta")
        tituloTextView.text = "Detalle de Venta N° ${venta.id}"
        descripcionTextView.text = venta.descripcion

        val caracteristicas = mutableListOf<DetalleCaracteristica>()

        detalles.forEach { detalle ->
            val precioUnitarioFmt = String.format("%.2f", detalle.precioUnitario)
            val subtotalFmt = String.format("%.2f", detalle.subtotal)

            caracteristicas.add(
                DetalleCaracteristica(
                    etiqueta = detalle.nombreProducto,
                    valor = "${detalle.cantidad} un. x $${precioUnitarioFmt} c/u | Subtotal: $${subtotalFmt}"
                )
            )
        }

        detalleRecyclerView.adapter = DetalleAdapter(caracteristicas)
    }

    private fun mostrarDialogoAcciones() {
        val opciones = mutableListOf<String>()

        opciones.add("Editar Detalles")

        if(currentCatalogType in listOf("LIBROS", "REVISTAS", "ARTICULOS")) {
            opciones.add("Ajustar Stock")
        }

        AlertDialog.Builder(this)
            .setTitle("Seleccionar Accion")
            .setItems(opciones.toTypedArray()) { _, which ->
                when (opciones[which]) {
                    "Editar Detalles" -> lanzarPantallaEdicion()
                    "Ajustar Stock" -> mostrarDialogoAjusteStock()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun lanzarPantallaEdicion() {
        val intent = Intent(this, P12_PantallaDeRegistroReutilizable::class.java).apply {
            putExtra(
                P12_PantallaDeRegistroReutilizable.EXTRA_MODE,
                P12_PantallaDeRegistroReutilizable.MODE_EDIT
            )
            putExtra(
                P12_PantallaDeRegistroReutilizable.EXTRA_FORM_TYPE,
                currentCatalogType
            )
            putExtra(
                P12_PantallaDeRegistroReutilizable.EXTRA_ITEM_ID,
                currentItemId
            )
        }
        editLauncher.launch(intent)
    }

    private fun mostrarDialogoAjusteStock() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_ajuste_stock, null)
        val cantidadInput = dialogView.findViewById<EditText>(R.id.input_cantidad)
        val btnAgregar = dialogView.findViewById<Button>(R.id.btn_agregar)
        val btnQuitar = dialogView.findViewById<Button>(R.id.btn_quitar)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Ajustar Stock")
            .setView(dialogView)
            .setNegativeButton("Cancelar", null)
            .create()

        btnAgregar.setOnClickListener {
            val cantidad = cantidadInput.text.toString().toIntOrNull()
            if (cantidad != null && cantidad > 0) {
                ajustarStock(cantidad)
                dialog.dismiss()
            } else {
                cantidadInput.error = "Ingrese una cantidad válida"
            }
        }

        btnQuitar.setOnClickListener {
            val cantidad = cantidadInput.text.toString().toIntOrNull()
            if (cantidad != null && cantidad > 0) {
                ajustarStock(-cantidad)
                dialog.dismiss()
            } else {
                cantidadInput.error = "Ingrese una cantidad válida"
            }
        }

        dialog.show()

    }

    private fun ajustarStock(cantidad: Int) {
        val tipoProducto = currentCatalogType
        val id = currentItemId

        if (tipoProducto == null || tipoProducto !in listOf("LIBROS", "REVISTAS", "ARTICULOS")) {
            Toast.makeText(this, "Esta operacion no es valida para este tipo de producto", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = creationRepository.ajustarStock(tipoProducto, id, cantidad)

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@P25_SeleccionElemento,
                        "Stock actualizado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    fetchItemDetails(id, tipoProducto)
                } else {
                    val codigoError = response.code()
                    val mensajeError = when(codigoError) {
                        404 -> "No se encontro el producto"
                        409 -> "Stock Insuficiente"
                        else -> "Error al actualizar el stock"
                    }

                    Toast.makeText(
                        this@P25_SeleccionElemento,
                        "Error: $mensajeError",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("AJUSTAR_STOCK_ERROR", "Error al ajustar el stock para $tipoProducto/$id", e)
                Toast.makeText(
                    this@P25_SeleccionElemento,
                    "Error de conexión",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun mostrarDialogoCambioEstado() {
        val estadosPosibles = arrayOf("PENDIENTE","RECIBIDO", "CANCELADO")

        AlertDialog.Builder(this)
            .setTitle("Selecciona un estado")
            .setItems(estadosPosibles) { _, which ->
                val nuevoEstado = estadosPosibles[which]
                actualizarEstadoPedido(nuevoEstado)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarEstadoPedido(nuevoEstado: String) {
        val pedidoActual = currentPedido ?: return

        lifecycleScope.launch {
            try {
                val extraData = mapOf("nuevoEstado" to nuevoEstado)
                val response = creationRepository.updateItem("PEDIDOS", pedidoActual.id, emptyMap(), extraData)

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@P25_SeleccionElemento,
                        "Estado del pedido actualizado a '${nuevoEstado}'",
                        Toast.LENGTH_SHORT
                    ).show()
                    fetchItemDetails(pedidoActual.id, "PEDIDOS")
                } else {
                    Toast.makeText(
                        this@P25_SeleccionElemento,
                        "Error al actualizar el estado del pedido: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@P25_SeleccionElemento,
                    "Error al actualizar: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
