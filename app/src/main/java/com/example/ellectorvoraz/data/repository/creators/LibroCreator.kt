package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.LibroRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.LibroValidator
import retrofit2.Response

class LibroCreator(private val api: ApiService) : ItemCreator {
    override suspend fun create(
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ) : Response<out Any> {

        LibroValidator.validate(data, extraData)

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
}