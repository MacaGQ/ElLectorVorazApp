package com.example.ellectorvoraz.data.network

import com.example.ellectorvoraz.data.model.Articulo_Escolar
import com.example.ellectorvoraz.data.model.Libro
import com.example.ellectorvoraz.data.model.LoginRequest
import com.example.ellectorvoraz.data.model.LoginResponse
import com.example.ellectorvoraz.data.model.Revista
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.Query



interface ApiService {

    // ----- Login -----

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // ----- Libros -----

    // Rutas GET para obtener libros de acuerdo a la query
    // Si la query esta vacía devuelve todos los libros
    @GET("libros")
    suspend fun getLibros(@Query("search") query: String?): Response<List<Libro>>

    // Ruta POST para agregar un libro
    @POST("libros")
    suspend fun agregarLibro(@Body libro: Libro): Response<Libro>


    // ----- Revistas -----

    // Ruta GET para obtener revistas
    // Si la query esta vacía devuelve todas las revistas
    @GET("revistas")
    suspend fun getRevistas(@Query("search") query: String?): Response<List<Revista>>


    // ----- Articulos Escolares -----

    // Ruta GET para obtener articulos
    // Si la query esta vacía devuelve todos los articulos
    @GET("articulos")
    suspend fun getArticulos(@Query("search") query: String?): Response<List<Articulo_Escolar>>

}