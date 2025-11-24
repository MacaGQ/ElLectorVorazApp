package com.example.ellectorvoraz.data.model


data class LoginResponse (
    val token: String,
    val user: UserInfo
)

data class UserInfo(
    val id: Int
)