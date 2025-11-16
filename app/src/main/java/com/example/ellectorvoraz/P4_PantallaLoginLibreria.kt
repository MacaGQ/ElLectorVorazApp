package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ellectorvoraz.P7_PantallaMenuOpcionesReutilizable.Companion.EXTRA_MENU_TYPE

class P4_PantallaLoginLibreria : Activity() {
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_p4_pantalla_login_libreria)
        val flechaVolver = findViewById<ImageView>(R.id.login_img_back)
        flechaVolver.setOnClickListener {
            finish()
        }

        val btnIngresar = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.login_btnLogin)
        btnIngresar.setOnClickListener {
            // Logica para iniciar sesion con autenticación
            val intent = Intent(this, P7_PantallaMenuOpcionesReutilizable::class.java)
            intent.putExtra(EXTRA_MENU_TYPE, "LIBRERIA_GENERAL")
            startActivity(intent)
        }

        val btnRegistro = findViewById<TextView>(R.id.login_txt_signup)
        btnRegistro.setOnClickListener {
            val intent = Intent(this, P5_PantallaRegistroLibreria::class.java)
            startActivity(intent)
        }
    }
}
