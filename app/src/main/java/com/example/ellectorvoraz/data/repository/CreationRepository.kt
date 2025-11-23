package com.example.ellectorvoraz.data.repository

import com.example.ellectorvoraz.data.network.ApiService
import com.example.ellectorvoraz.data.repository.creators.BookCreator
import com.example.ellectorvoraz.data.repository.creators.ItemCreator
import com.example.ellectorvoraz.data.repository.creators.MagazineCreator
import com.example.ellectorvoraz.data.repository.creators.ProveedorCreator
import com.example.ellectorvoraz.data.repository.creators.SchoolItemCreator
import retrofit2.Response

class CreationRepository (api: ApiService){

    // Item reutilizable para todos los elementos que deben registrarse

    private val creators: Map<String, ItemCreator> = mapOf(
        "LIBROS" to BookCreator(api),
        "REVISTAS" to MagazineCreator(api),
        "ARTICULOS" to SchoolItemCreator(api),
        "PROVEEDORES" to ProveedorCreator(api)
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
}