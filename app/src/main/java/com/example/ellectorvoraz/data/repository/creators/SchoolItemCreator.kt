package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.ArticuloRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class SchoolItemCreator (private val api: ApiService) : ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {
        validate(data, extraData)

        val proveedorId = extraData["proveedorId"] as Int

        val articuloRequest = ArticuloRequest(
            nombre = data["nombre"]!!,
            marca = data["marca"]!!,
            precio = data["precio"]!!.toDouble(),
            stock = data["stock"]!!.toInt(),
            seccion = data["seccion"]!!,
            codigo = data["codigo"]!!,
            proveedorId = proveedorId
        )

        return api.createArticulo(articuloRequest)
    }

    private fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
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