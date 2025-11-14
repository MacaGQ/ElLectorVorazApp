package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat


class P5_PantallaRegistroLibreria : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p5_pantalla_registro_libreria)

        val btnRegistrarse = findViewById<Button>(R.id.signup_btnSignup)
        btnRegistrarse.setOnClickListener {
            // Lógica para registrarse
        }

        val loginTextView = findViewById<TextView>(R.id.signup_txt_login)
        loginTextView.setOnClickListener {
            val intent = Intent(this, P4_PantallaLoginLibreria::class.java)
            startActivity(intent)
            this.finish()
        }

        val roles = findViewById<Spinner>(R.id.signup_spn_roles)

        val adapter = object : ArrayAdapter<CharSequence>(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.roles)
        ) {
            // Deshabilitar la primera opcion del spinner (hint)
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            // Cambiar color del item seleccionado para igualarlo a los demas elementos del form
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                val selectedItemColor = ContextCompat.getColor(context, R.color.form_text)

                textView.setTextColor(selectedItemColor)

                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roles.adapter = adapter

        val volverAtras = findViewById<ImageView>(R.id.signup_img_back)
        volverAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}