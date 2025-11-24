package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.RevistaRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class MagazineCreator (private val api: ApiService) :  ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ) : Response<out Any> {
        validate(data, extraData)

        val proveedorId = extraData["proveedorId"] as Int

        val revistaRequest = RevistaRequest(
            nombre = data["nombre"]!!,
            categoria = data["categoria"]!!,
            edicion = data["edicion"]!!,
            numero = data["numero"]!!.toInt(),
            issn = data["issn"]!!,
            precio = data["precio"]!!.toDouble(),
            stock = data["stock"]!!.toInt(),
            proveedorId = proveedorId
        )

        return api.createRevista(revistaRequest)
    }

    private fun validate(data: Map<String, String>, extraData: Map<String, Any?>) {
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