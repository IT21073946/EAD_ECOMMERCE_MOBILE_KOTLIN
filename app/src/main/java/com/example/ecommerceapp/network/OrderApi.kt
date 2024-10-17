package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.Order
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT

interface OrderApi {
    @GET("api/Order")
    fun getAllOrders(): Call<List<Order>>

    @GET("api/Order/{id}")
    fun getOrderById(@Path("id") orderId: String): Call<Order>  // Fetch order by order ID

    @POST("api/Order")
    fun createOrder(@Body order: Order): Call<Order>

    @PUT("api/Order/{id}")
    fun updateOrder(@Path("id") orderId: String, @Body order: Order): Call<Order>

    @GET("api/Order/user/{customerId}")
    fun getOrdersByCustomerId(@Path("customerId") customerId: String): Call<List<Order>>
}
