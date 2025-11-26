package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.ArticuloRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.ArticuloValidator
import retrofit2.Response

class ArticuloCreator (private val api: ApiService) : ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {
        ArticuloValidator.validate(data, extraData)

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
}