package com.example.ellectorvoraz.data.network

import com.example.ellectorvoraz.data.model.ArticuloRequest
import com.example.ellectorvoraz.data.model.Articulo_Escolar
import com.example.ellectorvoraz.data.model.DetallePedido
import com.example.ellectorvoraz.data.model.DetalleVenta
import com.example.ellectorvoraz.data.model.Libro
import com.example.ellectorvoraz.data.model.LibroRequest
import com.example.ellectorvoraz.data.model.LoginRequest
import com.example.ellectorvoraz.data.model.LoginResponse
import com.example.ellectorvoraz.data.model.Pedido
import com.example.ellectorvoraz.data.model.PedidoRequest
import com.example.ellectorvoraz.data.model.Proveedor
import com.example.ellectorvoraz.data.model.ProveedorRequest
import com.example.ellectorvoraz.data.model.RegisterRequest
import com.example.ellectorvoraz.data.model.RegisterResponse
import com.example.ellectorvoraz.data.model.Revista
import com.example.ellectorvoraz.data.model.RevistaRequest
import com.example.ellectorvoraz.data.model.Rol
import com.example.ellectorvoraz.data.model.Venta
import com.example.ellectorvoraz.data.model.VentaRequest
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ApiService {

    // ----- Login -----
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // ----- Registro -----
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    // Obtener roles para la pantalla de registro
    @GET("roles")
    suspend fun getRoles(): Response<List<Rol>>

    // ----- Libros -----

    // Ruta GET para obtener libros de acuerdo a la query (busqueda global o por filtros)
    // Si la query esta vacía devuelve todos los libros
    @GET("libros")
    suspend fun getLibros(@QueryMap options: Map<String, String>): Response<List<Libro>>

    // Ruta GET para obtener un libro de acuerdo al id para mostrar los detalles
    @GET("libros/{id}")
    suspend fun getLibroId(@Path("id") id: Int): Response<Libro>

    // Ruta POST para agregar un libro a la base
    @POST("libros")
    suspend fun createLibro(@Body libroRequest: LibroRequest): Response<Libro>

    @PUT("libros/{id}")
    suspend fun updateLibro(@Path("id") id: Int, @Body libroRequest: LibroRequest): Response<Libro>


    // ----- Revistas -----

    // Ruta GET para obtener revistas
    // Si la query esta vacía devuelve todas las revistas
    @GET("revistas")
    suspend fun getRevistas(@QueryMap options: Map<String, String>): Response<List<Revista>>

    @GET("revistas/{id}")
    suspend fun getRevistaId(@Path("id") id: Int): Response<Revista>

    @POST("revistas")
    suspend fun createRevista(@Body revistaRequest: RevistaRequest): Response<Revista>

    @PUT("revistas/{id}")
    suspend fun updateRevista(@Path("id") id: Int, @Body revistaRequest: RevistaRequest): Response<Revista>


    // ----- Articulos Escolares -----

    // Ruta GET para obtener articulos
    // Si la query esta vacía devuelve todos los articulos
    @GET("articulos")
    suspend fun getArticulos(@QueryMap options: Map<String, String>): Response<List<Articulo_Escolar>>

    @GET("articulos/{id}")
    suspend fun getArticuloId(@Path("id") id: Int): Response<Articulo_Escolar>

    @POST("articulos")
    suspend fun createArticulo(@Body articuloRequest: ArticuloRequest): Response<Articulo_Escolar>


    // ----- Pedidos -----
    @GET("pedidos")
    suspend fun getPedidos(@QueryMap options: Map<String, String>): Response<List<Pedido>>

    @GET("pedidos/{id}")
    suspend fun getPedidoId(@Path("id") id: Int): Response<Pedido>

    @GET("pedidos/{id}/detalles")
    suspend fun getDetallePedido(@Path("id") id: Int): Response<List<DetallePedido>>

    @POST("pedidos")
    suspend fun createPedido(@Body pedidoRequest: PedidoRequest): Response<Any>

    // ----- Proveedores -----
    @GET("proveedores")
    suspend fun getProveedores(@QueryMap options: Map<String, String>): Response<List<Proveedor>>

    @GET("proveedores/{id}")
    suspend fun getProveedorId(@Path("id") id: Int): Response<Proveedor>

    @POST("proveedores")
    suspend fun createProveedor(@Body proveedorRequest: ProveedorRequest): Response<Proveedor>


    // ----- Ventas -----

    @GET("ventas")
    suspend fun getVentas(@QueryMap options: Map<String, String>): Response<List<Venta>>

    @GET("ventas/{id}")
    suspend fun getVentaId(@Path("id") id: Int): Response<Venta>

    @GET("ventas/{id}/detalles")
    suspend fun getDetalleVenta(@Path("id") id: Int): Response<List<DetalleVenta>>

    @POST("ventas")
    suspend fun createVenta(@Body ventaRequest: VentaRequest): Response<Any>

}