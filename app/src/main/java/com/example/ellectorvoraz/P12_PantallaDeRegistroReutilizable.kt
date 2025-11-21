package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.FormAdapter
import com.example.ellectorvoraz.data.FormRepository
import com.example.ellectorvoraz.data.network.RetrofitClient
import com.example.ellectorvoraz.data.repository.CreationRepository
import kotlinx.coroutines.launch


class P12_PantallaDeRegistroReutilizable : BaseActivity() {

    companion object {
        const val EXTRA_FORM_TYPE = "EXTRA_FORM_TYPE"
    }

    private lateinit var adapter: FormAdapter
    private lateinit var formType: String

    private lateinit var creationRepository: CreationRepository
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

        val api = RetrofitClient.getInstance(this)
        creationRepository = CreationRepository(api)


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
        val extraData = mutableMapOf<String, Any?>()

        if (formType == "LIBROS" || formType == "REVISTAS" || formType == "ARTICULOS") {
            extraData["proveedorId"] = this.proveedorSeleccionadoId
        }

        lifecycleScope.launch {
            try {
                val response = creationRepository.createItem(formType, data, extraData)

                if (response.isSuccessful) {
                    Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Guardado exitoso", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e:Exception) {
                Toast.makeText(this@P12_PantallaDeRegistroReutilizable, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
