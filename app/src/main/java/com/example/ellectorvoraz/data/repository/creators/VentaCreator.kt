package com.example.ellectorvoraz.data.repository.creators

import com.example.ellectorvoraz.data.model.DetalleVentaRequest
import com.example.ellectorvoraz.data.model.VentaRequest
import com.example.ellectorvoraz.data.network.ApiService
import retrofit2.Response

class VentaCreator (private val api: ApiService) : ItemCreator {
    override suspend fun create (
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any> {
        val usuarioId = extraData["usuarioId"] as? Int ?: throw IllegalArgumentException("No se pudo identificar al venededor")

        @Suppress("UNCHECKED_CAST")
        val detalle = extraData["detalle"] as? List<DetalleVentaRequest> ?: throw IllegalArgumentException("El detalle de la venta no puede estar vacío")

        val total = extraData["total"] as? Double ?: throw IllegalArgumentException("El total de la venta no puede ser nulo")

        if (detalle.isEmpty()) {
            throw IllegalArgumentException("La venta debe contener al menos un producto")
        }

        val ventaRequest = VentaRequest(
            usuarioId = usuarioId,
            total = total,
            detalle = detalle
        )

        return api.createVenta(ventaRequest)
    }
}