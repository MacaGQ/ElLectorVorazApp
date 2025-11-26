package com.example.ellectorvoraz.data.repository

import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.creators.LibroCreator
import com.example.ellectorvoraz.data.repository.creators.ItemCreator
import com.example.ellectorvoraz.data.repository.creators.MagazineCreator
import com.example.ellectorvoraz.data.repository.creators.PedidoCreator
import com.example.ellectorvoraz.data.repository.creators.ProveedorCreator
import com.example.ellectorvoraz.data.repository.creators.SchoolItemCreator
import com.example.ellectorvoraz.data.repository.creators.VentaCreator
import com.example.ellectorvoraz.data.repository.updaters.ItemUpdater
import com.example.ellectorvoraz.data.repository.updaters.LibroUpdater

import retrofit2.Response

class CreationRepository (api: ApiService){

    // Item reutilizable para todos los elementos que deben registrarse

    private val creators: Map<String, ItemCreator> = mapOf(
        "LIBROS" to LibroCreator(api),
        "REVISTAS" to MagazineCreator(api),
        "ARTICULOS" to SchoolItemCreator(api),
        "PROVEEDORES" to ProveedorCreator(api),
        "PEDIDO" to PedidoCreator(api),
        "VENTA" to VentaCreator(api)
    )

    private val updaters: Map<String, ItemUpdater> = mapOf(
        "LIBROS" to LibroUpdater(api)
    )


    suspend fun createItem(
        formType: String,
        data: Map<String, String>,
        extraData: Map<String, Any?> = emptyMap()
    ): Response<out Any> {
        val creator = creators[formType]
            ?: throw IllegalArgumentException("No hay formulario definido para $formType")

        return creator.create(data, extraData)
    }

    suspend fun updateItem(
        type: String,
        id: Int,
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response <out Any> {
        val updater = updaters[type]
            ?: throw IllegalArgumentException("No hay actualizacion definida para $type")

        return updater.update(id, data, extraData)
    }
}