package com.example.ellectorvoraz

import android.os.Bundle
import android.content.Intent
import android.app.Activity
import android.os.Handler
import android.os.Looper

class P1_PantallaBienvenida : Activity() {

    private val splashTimeOut: Long = 2000 // 2 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p1_pantalla_bienvenida)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, P2_PantallaBienvenidaEleccion::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}

