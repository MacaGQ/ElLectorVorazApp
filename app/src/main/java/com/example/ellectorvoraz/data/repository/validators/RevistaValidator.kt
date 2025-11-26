package com.example.ellectorvoraz.data.repository.validators

object RevistaValidator {
    fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
        val requiredFields = listOf(
            "nombre",
            "categoria",
            "edicion",
            "numero",
            "issn",
            "precio",
            "stock"
        )

        for (key in requiredFields) {
            if (data[key].isNullOrBlank() && key != "proveedorId") {
                throw IllegalArgumentException("El campo $key es requerido")
            }
        }

        if (extraData["proveedorId"] == null || extraData["proveedorId"] as? Int == -1) {
            throw IllegalArgumentException("Debe seleccionar un proveedor")
        }

        if (data["numero"]?.toIntOrNull() == null) {
            throw IllegalArgumentException("El campo 'Numero' debe ser un numero")
        }

        if (data["issn"]?.length != 9) {
            throw IllegalArgumentException("El campo 'ISSN' debe tener 8 caracteres incluyendo el guión")
        }

        if (data["precio"]?.toDoubleOrNull() == null) {
            throw IllegalArgumentException("El campo 'Precio' debe ser un numero")
        }

        if (data["stock"]?.toIntOrNull() == null) {
            throw IllegalArgumentException("El campo 'Stock' debe ser un numero")
        }
    }
}