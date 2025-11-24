package com.example.ellectorvoraz

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.adapters.MenuButtonAdapter
import com.example.ellectorvoraz.data.MenuRepository
import com.example.ellectorvoraz.data.network.RetrofitClient
import com.example.ellectorvoraz.util.SharedPreferencesManager

class P7_PantallaMenuOpcionesReutilizable : BaseActivity() {

    // Datos necesarios para crear las diferentes pantallas con el mismo layout
    companion object {
        const val EXTRA_MENU_TYPE = "EXTRA_MENU_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p7_menu_opciones_reutilizable)

        // Obtener el menu de MenuData
        val menuScreen = MenuRepository.getMenuScreenForType(this, intent)

        if (menuScreen == null) {
            Toast.makeText(this, "Menu no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inicializacion de las barras de superior e inferior
        setupTopBar(menuScreen.title)
        setupBottomNav()

        // Carga de los datos anteriores en al UI
        val recyclerView = findViewById<RecyclerView>(R.id.menu_recycler_view)

        val adapter = MenuButtonAdapter(menuScreen.buttons)

        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
            showLogoutConfirmationDialog()
            true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Cerrar Sesión

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Está seguro de que quiere cerrar la sesión?")
            .setPositiveButton("Sí") { _, _ ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {

        SharedPreferencesManager.clearSession(this)
        RetrofitClient.clearInstance()
        val intent = Intent(this, P4_PantallaLoginLibreria::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}
