package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class P7_PantallaSistemaGeneral : Activity() {
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_p7_pantalla_sistema_general)

        val btnCatalogo = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnCatalogos)
        btnCatalogo.setOnClickListener(){
            val intent = Intent(this, P10_PantallaDeSistemaParticular::class.java)
            startActivity(intent)
        }

        val btnGestion = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnGestion)
        btnGestion.setOnClickListener(){
            val intent = Intent(this, P75_PantallaDeGestion::class.java)
            startActivity(intent)
        }
    }
}
