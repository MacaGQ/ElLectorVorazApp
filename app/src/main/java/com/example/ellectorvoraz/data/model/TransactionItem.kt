package com.example.ellectorvoraz.data.model

data class TransactionItem(
    val productoId : Int,
    val tipoProducto: String,
    val nombreProducto: String,
    var cantidad: Int = 1,
    var precioUnitario: Double,
    var subtotal: Double = precioUnitario,
    val stockMaximo: Int
)
