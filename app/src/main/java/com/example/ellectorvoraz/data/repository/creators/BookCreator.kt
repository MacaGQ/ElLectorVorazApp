package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.LibroRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class BookCreator(private val api: ApiService) : ItemCreator {
    override suspend fun create(
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ) : Response<out Any> {

        validate(data, extraData)

        val proveedorId = extraData["proveedorId"] as Int

        val libroRequest = LibroRequest(
            titulo = data["titulo"]!!,
            autor = data["autor"]!!,
            editorial = data["editorial"]!!,
            isbn = data["isbn"]!!,
            genero = data["genero"]!!,
            seccion = data["seccion"]!!,
            precio = data["precio"]!!.toDouble(),
            stock = data["stock"]!!.toInt(),
            proveedorId = proveedorId
        )

        return api.createLibro(libroRequest)
    }

    private fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
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

        if (extraData["proveedorId"] == null || extraData["proveedorId"] as? Int == -1) {
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