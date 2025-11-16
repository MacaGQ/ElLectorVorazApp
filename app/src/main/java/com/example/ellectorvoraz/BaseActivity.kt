package com.example.ellectorvoraz

import kotlin.reflect.KClass
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Setup de la barra superior
    protected fun setupTopBar(title: String) {
        supportActionBar?.apply {
            setDisplayShowCustomEnabled(true)

            // No muestra el título porque se maneja de un layout personalizado para centrarlo
            setDisplayShowTitleEnabled(false)

            // Mostrar botón de volver atrás
            setDisplayHomeAsUpEnabled(true)

            // Layout personalizado para centrar el título
            setCustomView(R.layout.action_bar_centered)

            // Cambiar el texto del título
            customView.findViewById<TextView>(R.id.action_bar_title).text = title
        }
    }

    //Comportamiento del boton volver atras
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Setup de la barra de navegacion inferior
    protected fun setupBottomNav() {
        // Layout de la barra de navegacion
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation) ?: return

        // Definir a qué pantalla ir según lo seleccionado
        bottomNav.setOnItemSelectedListener { item ->
            val nextActivity: KClass<*>? = when (item.itemId) {
                R.id.navigation_home -> P7_PantallaSistemaGeneral::class // COMPLETAR DEMAS OPCIONES
                else -> null
            }
            // Solo cambiar de pantalla si se toca una opcion distinta a la pantalla actual
            if (nextActivity != null && nextActivity != this::class) {
                val intent = Intent(this, nextActivity.java)
                startActivity(intent)
            }
            true
        }
    }
}