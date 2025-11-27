package com.example.ellectorvoraz.data.repository.updaters

import com.example.ellectorvoraz.data.model.RevistaRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.RevistaValidator
import retrofit2.Response

class RevistaUpdater (private val api: ApiService): ItemUpdater {
    override suspend fun update(
        id: Int,
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {

        RevistaValidator.validate(data, extraData)

        val proveedorId = extraData["proveedorId"] as? Int
            ?: throw IllegalArgumentException("El proveedorId es requerido")


        val request = RevistaRequest(
            nombre = data["nombre"]!!,
            categoria = data["categoria"]!!,
            edicion = data["edicion"]!!,
            numero = data["numero"]!!.toInt(),
            issn = data["issn"]!!,
            precio = data["precio"]!!.toDouble(),
            stock = 0,
            proveedorId = proveedorId,
        )

        return api.updateRevista(id, request)
    }
}