package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.FormAdapter
import com.example.ellectorvoraz.data.FormRepository
import com.example.ellectorvoraz.data.model.LibroRequest
import com.example.ellectorvoraz.data.network.RetrofitClient
import kotlinx.coroutines.launch


class P12_PantallaDeRegistroReutilizable : BaseActivity() {

    companion object {
        const val EXTRA_FORM_TYPE = "EXTRA_FORM_TYPE"
    }

    private lateinit var adapter: FormAdapter
    private lateinit var formType: String

    private var proveedorSeleccionadoId: Int? = null

    private val lanzadorSeleccion = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            proveedorSeleccionadoId = data?.getIntExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_ID, -1)
            val nombreProveedor = data?.getStringExtra(P21_PantallaCatalogoReutilizable.RESULT_SELECTED_NAME)

            if (proveedorSeleccionadoId != -1 && nombreProveedor != null) {
                adapter.updateFieldValue("proveedorId", nombreProveedor)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p12_pantalla_de_registro_reutilizable)

        // Obtener el tipo de form a crear (defaultea a registro de libro)
        // El tipo de form se define en el intent de la actividad anterior
        // El contenido del form se crea en data/FormData
        formType = intent.getStringExtra(EXTRA_FORM_TYPE) ?: ""

        if (formType.isEmpty()) {
            Toast.makeText(this, "Error: Formulario no definido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val formScreen = FormRepository.getFormForType(formType) ?: run {
            Toast.makeText(this, "Error: Formulario no definido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inicializacion de las barras de superior e inferior
        setupTopBar(formScreen.title)
        setupBottomNav()

        // Cargar el RecyclerView con los campos
        val recyclerView = findViewById<RecyclerView>(R.id.form_recycler_view)
        adapter = FormAdapter(formScreen.fields) { fieldKey, entityType ->
            when (entityType) {
                "PROVEEDOR" -> {
                    val intent = Intent(this, P21_PantallaCatalogoReutilizable::class.java)
                    intent.putExtra(
                        P21_PantallaCatalogoReutilizable.EXTRA_CATALOG_TYPE,
                        "PROVEEDORES"
                    )
                    intent.putExtra(
                        P21_PantallaCatalogoReutilizable.EXTRA_OPERATION_MODE,
                        P21_PantallaCatalogoReutilizable.MODE_SELECTION
                    )

                    lanzadorSeleccion.launch(intent)
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Selector no definido para: $entityType",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        recyclerView.adapter = adapter

        // Texto del boton de registro
        val submitButton = findViewById<Button>(R.id.btnRegistrar)
        submitButton.text = getString(R.string.btn_registrar)
        submitButton.setOnClickListener {
            // Lo ingresado pro el usuario esta en adapter.formData
            val submittedData = adapter.formData
            handleSubmit(formType, submittedData)
        }
    }

    private fun handleSubmit(formType: String, data: Map<String, String>) {

        if(!validateForm(formType, data)) {
            return
        }

        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P12_PantallaDeRegistroReutilizable)
                var success = false

                when (formType) {
                    "LIBROS" -> {
                        // Guardar el libro en la BBDD (data)
                        val libroRequest = LibroRequest(
                            titulo = data["titulo"]!!,
                            autor = data["autor"]!!,
                            editorial = data["editorial"]!!,
                            isbn = data["isbn"]!!,
                            genero = data["genero"]!!,
                            seccion = data["seccion"]!!,
                            precio = data["precio"]!!.toDouble(),
                            stock = data["stock"]!!.toInt(),
                            proveedorId = proveedorSeleccionadoId!!
                        )

                        val response = api.createLibro(libroRequest)
                        success = response.isSuccessful
                    }
                    else -> {
                        Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Error: Guardado no definido", Toast.LENGTH_SHORT).show()
                    }
                }

                if (success) {
                    Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Guardado exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Error: No se pudo guardar", Toast.LENGTH_SHORT).show()
                }
            } catch (e:Exception) {
                Log.e("API_CALL_ERROR", "Fallo al registrar: ${e.message}", e)
                Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Error de conexion", Toast.LENGTH_SHORT).show()
            }
        }


        // Ir a la siguiente pantalla

    }

    private fun validateForm(formType: String, data: Map<String, String>): Boolean {
        val requiredFields = when (formType) {
            "LIBROS" -> listOf(
                "titulo",
                "autor",
                "editorial",
                "isbn",
                "genero",
                "seccion",
                "precio",
                "stock"
            )

            else -> emptyList()
        }

        for (key in requiredFields) {
            if (data[key].isNullOrBlank() && key != "proveedorId") {
                val fieldLabel = adapter.fields.find { it.key == key }?.label ?: key
                Toast.makeText(this, "Error: $fieldLabel es requerido", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        if (formType == "LIBROS") {
            if (proveedorSeleccionadoId == null || proveedorSeleccionadoId == -1) {
                Toast.makeText(this, "Error: Se debe seleccionar un proveedor", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        if (data.containsKey("stock") && data["stock"]?.toIntOrNull() == null) {
            Toast.makeText(this, "Error: Stock debe ser un numero", Toast.LENGTH_SHORT).show()
            return false
        }
        if (data.containsKey("precio") && data["precio"]?.toDoubleOrNull() == null) {
            Toast.makeText(this, "Error: Precio debe ser un numero", Toast.LENGTH_SHORT).show()
            return false
        }

        if (data.containsKey("isbn") && data["isbn"]?.length != 13) {
            Toast.makeText(this, "Error: El ISBN debe ingresarse SIN guiones y tener 13 digitos", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
