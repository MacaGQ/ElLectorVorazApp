package com.example.ellectorvoraz

import android.content.Intent
import android.os.Bundle

class P7_PantallaSistemaGeneral : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p7_pantalla_sistema_general)

        setupTopBar(getString(R.string.sector_libreria))
        setupBottomNav()

        val btnCatalogo = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnCatalogos)
        btnCatalogo.setOnClickListener(){
            val intent = Intent(this, P9_PantallaDeCatalogos::class.java)
            startActivity(intent)
        }

        val btnGestion = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnGestion)
        btnGestion.setOnClickListener(){
            val intent = Intent(this, P75_PantallaDeGestion::class.java)
            startActivity(intent)
        }
    }
}
