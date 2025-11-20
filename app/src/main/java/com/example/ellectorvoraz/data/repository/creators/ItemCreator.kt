package com.example.ellectorvoraz.data.repository.creators

import retrofit2.Response

interface ItemCreator {
    suspend fun create(
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any>
}