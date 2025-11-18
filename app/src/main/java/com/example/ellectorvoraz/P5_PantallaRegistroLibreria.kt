package com.example.ellectorvoraz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.core.content.ContextCompat
import com.example.ellectorvoraz.data.model.RegisterRequest
import com.example.ellectorvoraz.data.model.Rol
import com.example.ellectorvoraz.data.network.RetrofitClient
import kotlinx.coroutines.launch


class P5_PantallaRegistroLibreria : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rolesSpinner: Spinner

    // Lista para guardar los roles obtenidos de la API
    private var rolesList: List<Rol> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_p5_pantalla_registro_libreria)

        usernameEditText = findViewById(R.id.signup_input_username)
        passwordEditText = findViewById(R.id.signup_input_password)
        rolesSpinner = findViewById(R.id.signup_spn_roles)

        val btnRegistrarse = findViewById<Button>(R.id.signup_btnSignup)
        btnRegistrarse.setOnClickListener {
            handleRegistration()
        }

        val volverAtras = findViewById<ImageView>(R.id.signup_img_back)
        volverAtras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        fetchRoles()
    }

    private fun fetchRoles() {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.getInstance(this@P5_PantallaRegistroLibreria)
                val response = api.getRoles()

                if (response.isSuccessful && response.body() != null) {
                    rolesList = response.body()!!
                    setupSpinner(rolesList)
                } else {
                    Toast.makeText(
                        this@P5_PantallaRegistroLibreria,
                        "Error al obtener los roles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("ROLES_ERROR", "Error al obtener los roles", e)
                Toast.makeText(
                    this@P5_PantallaRegistroLibreria,
                    "Error de conexión",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupSpinner(roles: List<Rol>) {
        val roleNames = roles.map { it.nombre.replaceFirstChar { char -> char.uppercase() } }

        // Se define la hint para Roles
        val spinnerDisplayList = mutableListOf<CharSequence>("Rol")

        // Se agregan los roles de la BBDD
        spinnerDisplayList.addAll(roleNames)

        val adapter = object : ArrayAdapter<CharSequence>(
            this,
            android.R.layout.simple_spinner_item,
            spinnerDisplayList
        ) {
            // Se deshabilita la primera opcion del spinner (hint)
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            // Se cambia el color del item seleccionado para igualarlo a los demas elementos del form
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                val selectedItemColor = ContextCompat.getColor(context, R.color.form_text)

                textView.setTextColor(selectedItemColor)

                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolesSpinner.adapter = adapter
    }

    private fun handleRegistration() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (rolesSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Por favor, selecciona un rol", Toast.LENGTH_SHORT).show()
            return
        }

        // Se resta uno ya que esta lista no tiene la hint
        val selectedRoleObject = rolesList[rolesSpinner.selectedItemPosition - 1]
        val selectedRole = selectedRoleObject.nombre

        performRegistration(username, password, selectedRole)
    }

    private fun performRegistration(username: String, password: String, role: String) {
        lifecycleScope.launch {
            try {
                val request = RegisterRequest(username, password, role.lowercase())
                val api = RetrofitClient.getInstance(this@P5_PantallaRegistroLibreria)
                val response = api.register(request)

                if (response.isSuccessful) {
                    goToSuccessScreen()
                } else if (response.code() == 409) {
                    Toast.makeText(
                        this@P5_PantallaRegistroLibreria,
                        "El nombre usuario ya existe. Por favor, elija otro",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@P5_PantallaRegistroLibreria,
                        "Error en el registro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e("REGISTER_ERROR", "Error en el registro", e)
                Toast.makeText(
                    this@P5_PantallaRegistroLibreria,
                    "Error en el registro",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun goToSuccessScreen() {
        val intent = Intent(this@P5_PantallaRegistroLibreria, P6_PantallaRegistroResultadoReutilizable::class.java)
        intent.putExtra("task", "register")
        intent.putExtra("status", "success")
        startActivity(intent)
        this.finish()
    }

}