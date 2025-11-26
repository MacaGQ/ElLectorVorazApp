package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class PedidoUpdateRequest(
    @SerializedName("estado")
    val estado: String
)
