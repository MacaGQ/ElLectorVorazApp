package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.RevistaRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.RevistaValidator
import retrofit2.Response

class RevistaCreator (private val api: ApiService) :  ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ) : Response<out Any> {
        RevistaValidator.validate(data, extraData)

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
}