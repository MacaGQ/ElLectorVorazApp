package com.example.ellectorvoraz

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.data.FormRepository


class P12_PantallaDeRegistroLibro : BaseActivity() {

    companion object {
        const val EXTRA_FORM_TYPE = "EXTRA_FORM_TYPE"
    }

    private lateinit var adapter: FormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p12_pantalla_de_registro_libro)

        // Obtener el tipo de form a crear (defaultea a registro de libro)
        val formType = intent.getStringExtra(EXTRA_FORM_TYPE) ?: "REGISTRO_LIBRO"
        val formScreen = FormRepository.getFormForType(formType)

        if (formScreen == null) {
            Toast.makeText(this, "Error: Formulario no definido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inicializacion de las barras de superior e inferior
        setupTopBar(formScreen.title)
        setupBottomNav()

        // Cargar el RecyclerView con los campos
        val recyclerView = findViewById<RecyclerView>(R.id.form_recycler_view)
        adapter = FormAdapter(formScreen.fields)
        recyclerView.adapter = adapter

        // Texto del boton de registro
        val submitButtonText = findViewById<TextView>(R.id.btnRegistrar)
        submitButtonText.text = getString(R.string.btn_registrar)

        // Funcionalidad del boton de registro
        val submitButton = findViewById<TextView>(R.id.btnRegistrar)
        submitButton.setOnClickListener {
            // Lo ingresado pro el usuario esta en adapter.formData
            val submittedData = adapter.formData
            handleSubmit(formType, submittedData)
        }

    }

    private fun handleSubmit(formType: String, data: Map<String, String>) {
        when (formType) {
            "REGISTRO_LIBRO" -> {
                // Guardar el libro en la BBDD (data)
                Toast.makeText(this, "Libro registrado", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Error: Guardado no definido", Toast.LENGTH_SHORT).show()
            }
        }

        // Ir a la siguiente pantalla

    }

}
