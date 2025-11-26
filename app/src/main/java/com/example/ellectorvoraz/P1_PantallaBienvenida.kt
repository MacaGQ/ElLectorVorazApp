package com.example.ellectorvoraz
import android.os.Bundle
import android.widget.ImageView
import android.content.Intent
import android.app.Activity

class P1_PantallaBienvenida : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p1_pantalla_bienvenida)

        val miImagen = findViewById<ImageView>(R.id.miImagen)

        miImagen.setOnClickListener {
            val intent = Intent(this, P2_PantallaBienvenidaEleccion::class.java)
            startActivity(intent)
        }
    }
}

