package com.example.ellectorvoraz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ellectorvoraz.data.model.LoginRequest
import com.example.ellectorvoraz.data.network.RetrofitClient
import com.example.ellectorvoraz.P7_PantallaMenuOpcionesReutilizable.Companion.EXTRA_MENU_TYPE
import com.example.ellectorvoraz.util.SharedPreferencesManager
import kotlinx.coroutines.launch


class P4_PantallaLoginLibreria : BaseActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p4_pantalla_login_libreria)

        // Inicializacion de la barra superior
        setupTopBar("Iniciar Sesión")

        usernameEditText = findViewById(R.id.login_input_username)
        passwordEditText = findViewById(R.id.login_input_password)

        val btnIngresar = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.login_btnLogin)
        btnIngresar.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(this, "Por favor, completar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        val btnRegistro = findViewById<TextView>(R.id.login_txt_signup)
        btnRegistro.setOnClickListener {
            val intent = Intent(this, P5_PantallaRegistroLibreria::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val request = LoginRequest(username, password)
                val api = RetrofitClient.getInstance(this@P4_PantallaLoginLibreria)
                val response = api.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    SharedPreferencesManager.saveSession(
                        this@P4_PantallaLoginLibreria,
                        loginResponse.token,
                        loginResponse.user.id
                    )
                    goToNextScreen()
                } else {
                    Toast.makeText(
                        this@P4_PantallaLoginLibreria,
                        "Credenciales incorrectas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e:Exception) {
                Log.e("LOGIN_ERROR", "Error al iniciar sesión", e)
                Toast.makeText(this@P4_PantallaLoginLibreria, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToNextScreen() {
        val intent = Intent(this, P7_PantallaMenuOpcionesReutilizable::class.java)
        intent.putExtra(EXTRA_MENU_TYPE, "LIBRERIA_GENERAL")
        startActivity(intent)
        finish()
    }

}
