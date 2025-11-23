package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.ProveedorRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class ProveedorCreator (private val api: ApiService) : ItemCreator {
    override suspend fun create (data: Map<String, String>, extraData: Map<String, Any?>) : Response<out Any> {
        validate (data)

        val proveedorRequest = ProveedorRequest(
            nombre = data["nombre"]!!,
            telefono = data["telefono"]!!,
            email = data["email"]!!,
            direccion = data["direccion"]!!,
            categoria = data["categoria"]!!
        )

        return api.createProveedor(proveedorRequest)
    }

    private fun validate(data: Map<String, String>) {
        val requiredFields = listOf("nombre", "telefono", "email", "direccion", "categoria")

        for (key in requiredFields) {
            if (data[key].isNullOrBlank()) {
                throw IllegalArgumentException("El campo '$key' no puede estar vacío")
            }
        }
    }

}