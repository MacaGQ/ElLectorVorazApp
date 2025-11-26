package com.example.ellectorvoraz.data.repository.updaters

import com.example.ellectorvoraz.data.model.PedidoUpdateRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class PedidoUpdater (private val api: ApiService): ItemUpdater {
    override suspend fun update (
        id: Int,
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {

        val nuevoEstado = extraData["nuevoEstado"] as? String
            ?: throw IllegalArgumentException("El estado es requerido")

        val request = PedidoUpdateRequest(
            estado = nuevoEstado
        )

        return api.updatePedido(id, request)

    }
}