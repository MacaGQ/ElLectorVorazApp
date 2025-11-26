package com.example.ellectorvoraz.data.repository.validators

object ArticuloValidator {
    fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
        val requiredFields = listOf(
            "nombre",
            "marca",
            "precio",
            "stock",
            "seccion",
            "codigo"
        )

        for (key in requiredFields) {
            if (data[key].isNullOrBlank() && extraData[key] == null) {
                throw IllegalArgumentException("El campo $key es requerido")
            }
        }

        if (extraData["proveedorId"] == null || extraData["proveedorId"] as? Int == -1) {
            throw IllegalArgumentException("Debe seleccionar un proveedor")
        }

        if (data["precio"]?.toDoubleOrNull() == null) {
            throw IllegalArgumentException("El campo 'Precio' debe ser un número")
        }

        if (data["stock"]?.toIntOrNull() == null) {
            throw IllegalArgumentException("El campo 'Stock' debe ser un número")
        }

    }
}