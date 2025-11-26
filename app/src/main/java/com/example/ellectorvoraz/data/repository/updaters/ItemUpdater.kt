package com.example.ellectorvoraz.data.repository.updaters

import retrofit2.Response

interface ItemUpdater {
    suspend fun update(
        id: Int,
        data: Map<String, String>,
        extraData: Map<String, Any?>
    ): Response<out Any>

}