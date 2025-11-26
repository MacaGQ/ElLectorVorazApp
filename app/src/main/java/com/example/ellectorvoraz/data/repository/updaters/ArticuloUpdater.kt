package com.example.ellectorvoraz.data.repository.updaters

import com.example.ellectorvoraz.data.model.ArticuloRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.ArticuloValidator
import retrofit2.Response

class ArticuloUpdater (private val api: ApiService): ItemUpdater {
    override suspend fun update (
        id: Int,
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {
        ArticuloValidator.validate(data, extraData)

        val proveedorId = extraData["proveedorId"] as? Int
            ?: throw IllegalArgumentException("El proveedorId es requerido")

        val request = ArticuloRequest(
            nombre = data["nombre"]!!,
            marca = data["marca"]!!,
            precio = data["precio"]!!.toDouble(),
            stock = data["stock"]!!.toInt(),
            seccion = data["seccion"]!!,
            codigo = data["codigo"]!!,
            proveedorId = proveedorId
        )
        return api.updateArticulo(id, request)
    }
}