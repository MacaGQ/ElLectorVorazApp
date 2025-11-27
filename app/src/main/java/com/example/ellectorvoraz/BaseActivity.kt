package com.example.ellectorvoraz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.size
import androidx.core.view.get

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
        Log.d("BottomNavDebug", "setupBottomNav() llamado desde ${this::class.simpleName}")
        // Layout de la barra de navegacion
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation) ?: return

        // Definir a qué pantalla ir según lo seleccionado
        bottomNav.setOnItemSelectedListener { item ->
            Log.d("BottomNavDebug", "Item seleccionado: ${item.title}")

            if (item.isChecked) {
                return@setOnItemSelectedListener true
            }
            handleNavigation(item.itemId)

            bottomNav.post{
                updateBottomNavSelection(bottomNav)
            }
        }
    }

    private fun handleNavigation(itemId: Int) {
        Log.d("BottomNavDebug", "handleNavigation() llamado con itemId: $itemId")
        when (itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, P7_PantallaMenuOpcionesReutilizable::class.java).apply {
                    putExtra(P7_PantallaMenuOpcionesReutilizable.EXTRA_MENU_TYPE, "LIBRERIA_GENERAL")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            }
            R.id.navigation_shopping -> {
                val intent = Intent(this, P82_PantallaTrasnsaccionesReutilizable::class.java).apply {
                    putExtra(
                        P82_PantallaTrasnsaccionesReutilizable.EXTRA_TRANSACTION_TYPE,
                        P82_PantallaTrasnsaccionesReutilizable.TYPE_VENTA
                    )
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            }
            R.id.navigation_notifications -> {
                Toast.makeText(
                    this,
                    "Sección 'Avisos' en desarrollo",
                    Toast.LENGTH_SHORT
                ).show()
            }
            R.id.navigation_profile -> {
                val intent = Intent(this, P25_SeleccionElemento::class.java).apply {
                    putExtra("EXTRA_CATALOG_TYPE", "PERFIL_USUARIO")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (bottomNav != null) {
            updateBottomNavSelection(bottomNav)
        }
    }

    private fun updateBottomNavSelection(bottomNav: BottomNavigationView) {

        bottomNav.menu.setGroupCheckable(0, true, false)
        for (i in 0 until bottomNav.menu.size) {
            bottomNav.menu[i].isChecked = false
        }
        bottomNav.menu.setGroupCheckable(0, true, true)

        val itemToSelect = when (this) {
            is P7_PantallaMenuOpcionesReutilizable -> {
                if(intent.getStringExtra("EXTRA_MENU_TYPE") == "LIBRERIA_GENERAL") {
                    R.id.navigation_home
                } else {
                    null
                }
            }
            is P82_PantallaTrasnsaccionesReutilizable -> {
                if (intent.getStringExtra("EXTRA_TRANSACTION_TYPE") == P82_PantallaTrasnsaccionesReutilizable.TYPE_VENTA) {
                    R.id.navigation_shopping
                } else {
                    null
                }
            }
            is P25_SeleccionElemento -> {
                if(intent.getStringExtra("EXTRA_CATALOG_TYPE") == "PERFIL_USUARIO") {
                    R.id.navigation_profile
                } else {
                    null
                }
            }
            else -> null
        }

        if (itemToSelect != null) {
            bottomNav.menu.findItem(itemToSelect).isChecked = true
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}