package com.example.ellectorvoraz
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import android.app.Activity

class P3_PantallaBienvenidaLibreria : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p3_pantalla_bienvenida_libreria)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {

            finish()

        }

        val miImagenLibreria = findViewById<ImageView>(R.id.miImagenLibreria)

        miImagenLibreria.setOnClickListener {
            val intent = Intent(this, P4_PantallaLoginLibreria::class.java)
            startActivity(intent)
        }
    }


}

