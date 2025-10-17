package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class P4_PantallaLoginLibreria : Activity() {
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_p4_pantalla_login_libreria)
        val flechaVolver = findViewById<ImageView>(R.id.flechaVolver)
        flechaVolver.setOnClickListener {
            finish()
        }

        val btnIngresar = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnIngresar)
        btnIngresar.setOnClickListener {
            val intent = Intent(this, P7_PantallaSistemaGeneral::class.java)
            startActivity(intent)
        }

        val btnRegistro = findViewById<TextView>(R.id.textView_Pregunta_registro)
        btnRegistro.setOnClickListener {
            val intent = Intent(this, P5_PantallaRegistroLibreria::class.java)
            startActivity(intent)
        }
    }
}
