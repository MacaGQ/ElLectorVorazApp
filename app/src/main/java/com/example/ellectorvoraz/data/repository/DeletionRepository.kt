package com.example.ellectorvoraz.data.repository

import android.util.Log
import com.example.ellectorvoraz.data.network.ApiService

class DeletionRepository (private val api: ApiService) {

    suspend fun deleteItem(itemType: String, itemId: Int): Boolean {
        return try {
            val response = when (itemType) {
                "LIBROS" -> api.deleteLibro(itemId)
                else -> null
            }
            response?.isSuccessful ?: false
        } catch (e: Exception) {
            Log.e("DELETION_REPOSITORY_ERROR", "Error al eliminar: ${e.message}", e)
            false
        }
    }

}