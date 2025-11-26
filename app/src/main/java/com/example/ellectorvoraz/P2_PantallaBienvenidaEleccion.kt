package com.example.ellectorvoraz
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import android.app.Activity
import android.app.ActivityOptions

class P2_PantallaBienvenidaEleccion : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p2_pantalla_bienvenida_eleccion)

        val miImagenCafeteria = findViewById<ImageView>(R.id.miImagenCafeteria)
        val miImagenLibreria = findViewById<ImageView>(R.id.miImagenLibreria)

        miImagenCafeteria.setOnClickListener {
            val intent = Intent(this, P4_PantallaLoginLibreria::class.java)
            startActivity(intent)
        }

        miImagenLibreria.setOnClickListener {
            val intent = Intent(this, P3_PantallaBienvenidaLibreria::class.java)

            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                miImagenLibreria,
                "logo_transicion")

            startActivity(intent, options.toBundle())
        }
    }
}

