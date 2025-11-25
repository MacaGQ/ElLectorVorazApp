package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.DetallePedidoRequest
import com.example.ellectorvoraz.data.model.PedidoRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class PedidoCreator (private val api: ApiService) : ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {
        val proveedorId = extraData["proveedorId"] as? Int ?: throw IllegalArgumentException("Debe seleccionar un proveedor")

        @Suppress("UNCHECKED_CAST")
        val detalle = extraData["detalle"] as? List<DetallePedidoRequest> ?: throw IllegalArgumentException("El detalle del pedido no puede estar vacío")

        if (detalle.isEmpty()) {
            throw IllegalArgumentException("El pedido debe contener al menos un producto")
        }

        val pedidoRequest = PedidoRequest(
            proveedorId = proveedorId,
            estado = extraData["estado"] as? String ?: "pendiente",
            detalle = detalle
        )

        return api.createPedido(pedidoRequest)
    }
}