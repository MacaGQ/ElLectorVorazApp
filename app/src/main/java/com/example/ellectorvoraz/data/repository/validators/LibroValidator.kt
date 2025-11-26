package com.example.ellectorvoraz.data.repository.validators

object LibroValidator {
    fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
        val requiredFields = listOf(
            "titulo",
            "autor",
            "editorial",
            "isbn",
            "genero",
            "seccion",
            "precio",
            "stock"
        )

        for (key in requiredFields) {
            if (data[key].isNullOrBlank() && key != "proveedorId") {
                throw IllegalArgumentException("El campo $key no puede estar vacío")
            }
        }

        if ((extraData["proveedorId"] as? Int ?: 0) <= 0)  {
            throw IllegalArgumentException("Debe seleccionar un proveedor")
        }

        if (data["stock"]?.toIntOrNull() == null) {
            throw IllegalArgumentException("El valor de 'Stock' debe ser un número entero")
        }
        if (data["precio"]?.toDoubleOrNull() == null) {
            throw IllegalArgumentException("El valor de 'Precio' debe ser un número entero")
        }

        if (data["isbn"]?.length != 13) {
            throw IllegalArgumentException("El ISBN debe tener 13 dígitos")
        }
    }
}