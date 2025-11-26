package com.example.ellectorvoraz
import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.os.Handler
import android.os.Looper

class P3_PantallaBienvenidaLibreria : Activity() {

    private val splashTimeOut: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p3_pantalla_bienvenida_libreria)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, P4_PantallaLoginLibreria::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}

