package com.example.ellectorvoraz.data.repository.updaters

import com.example.ellectorvoraz.data.model.ProveedorRequest
import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.validators.ProveedorValidator
import retrofit2.Response

class ProveedorUpdater (private val api: ApiService): ItemUpdater {
    override suspend fun update(
     id: Int,
     data: Map<String, String>,
     extraData: Map<String, Any?>
    ): Response<out Any> {

        ProveedorValidator.validate(data)

        val proveedorRequest = ProveedorRequest(
            nombre = data["nombre"]!!,
            telefono = data["telefono"]!!,
            email = data["email"]!!,
            direccion = data["direccion"]!!,
            categoria = data["categoria"]!!
        )

        return api.updateProveedor(id, proveedorRequest)
    }
}