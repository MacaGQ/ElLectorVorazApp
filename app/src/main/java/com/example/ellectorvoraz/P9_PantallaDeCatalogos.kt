package com.example.ellectorvoraz

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView

class P9_PantallaDeCatalogos : Activity() {
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_p9_pantalla_de_catalogos)

        val btnVolver = findViewById<ImageView>(R.id.flechaVolver)
        btnVolver.setOnClickListener(){
            finish()
        }
    }
}
