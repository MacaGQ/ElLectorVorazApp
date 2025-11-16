package com.example.ellectorvoraz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class P6_PantallaRegistroResultadoReutilizable : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p6_pantalla_registro_resultado)
        val task = intent.getStringExtra("task")
        val status = intent.getStringExtra("status")

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        var nextScreen: Class<out Activity>? =
            P7_PantallaMenuOpcionesReutilizable::class.java


        // Cambiar ícono y el texto de acuerdo al resultado de la operación
        val imgSuccess = findViewById<ImageView>(R.id.img_success)
        val imgError = findViewById<ImageView>(R.id.img_error)
        val txtResultado = findViewById<TextView>(R.id.txt_info)

        // Cambiar el texto de info de acuerdo al escenario (task)
        // Escenarios posibles:
        // 1. Se inserto un libro (correcto/incorrecto) (P13/14)
        // 2. Se inserto una revista (correcto/incorrecto) (P16/17)
        // 3. Se inserto un articulo de libreria (correcto/incorrecto) (P19/20)
        // 4. Registro de usuario (correcto/incorrecto) (P6) + (P6 Cafeteria)
        // 5. Rol insuficiente (P7A/8)
        // 6. Pedido registrado (correcto/incorrecto) (P83)
        // 7. Venta registrada (correcto/incorrecto) (P93)
        // 8. Proveedor registrado (correcto/incorrecto) (P7A)
        // 9. Item agregado al menu (correcto/incorrecto) (P7A Cafeteria)
        // 10. Pago realizado (correcto/incorrecto) (P16 Cafeteria)

        val operacionExitosa = status == "success"

        // Se construye el mensaje a mostrar por pantalla, primero que item se añadió (o no) a la base, despues el resultado de la operacion
        var mensaje = ""

        val result = when (task) {
            "book" -> getString(R.string.book)
            "magazine" -> getString(R.string.magazine)
            "supply" -> getString(R.string.supply)
            "register" -> getString(R.string.user)
            else -> "" // Agregar caso default
        }
        mensaje += result

        if (operacionExitosa) {
            // Si la operación fue exitosa, mostrar ícono de éxito, ocultar icono de error y completar el mensaje de exito.
            imgSuccess.visibility = View.VISIBLE
            imgError.visibility = View.GONE
            mensaje += " " + getString(R.string.success)
        } else {
            // Si la operación no fue exitosa, mostrar ícono de error, ocultar icono de éxito y completar el mensaje de error.
            imgSuccess.visibility = View.GONE
            imgError.visibility = View.VISIBLE
            mensaje += " " + getString(R.string.error)
        }

        txtResultado.text = mensaje

        // Si se viene del registro de usuario, cambiar el boton a inciar sesión y dirigirlo al Login
        if (task == "register") {
            btnVolver.text = getString(R.string.action_sign_in)
            nextScreen = P4_PantallaLoginLibreria::class.java
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, nextScreen)
            startActivity(intent)
        }
    }
}
