package com.example.ellectorvoraz.data.repository.validators

object ProveedorValidator {
    fun validate(data: Map<String, String>) {
        val requiredFields = listOf("nombre", "telefono", "email", "direccion", "categoria")

        for (key in requiredFields) {
            if (data[key].isNullOrBlank()) {
                throw IllegalArgumentException("El campo '$key' no puede estar vacío")
            }
        }
    }
}