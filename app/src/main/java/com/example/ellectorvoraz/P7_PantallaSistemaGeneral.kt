package com.example.ellectorvoraz

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ellectorvoraz.data.MenuRepository

class P7_PantallaSistemaGeneral : BaseActivity() {

    // Datos necesarios para crear las diferentes pantallas con el mismo layout
    companion object {
        const val EXTRA_MENU_TYPE = "EXTRA_MENU_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p7_pantalla_sistema_general)


        // Datos recibidos del intent en la pantalla anterior
        val menuType = intent.getStringExtra(EXTRA_MENU_TYPE) ?: "none"

        // Obtener el menu de MenuData
        val menuScreen = MenuRepository.getMenuScreenForType(this, menuType)

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
}
