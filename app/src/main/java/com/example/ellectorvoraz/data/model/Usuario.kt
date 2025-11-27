package com.example.ellectorvoraz.data.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("rol_id")
    val rolId: Int,

    @SerializedName("created_at")
    val createdAt: String?
)
