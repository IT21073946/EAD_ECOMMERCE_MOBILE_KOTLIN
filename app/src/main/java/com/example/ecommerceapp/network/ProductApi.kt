package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {

    // GET request to fetch all products
    @GET("/api/Product")
    fun getProducts(): Call<List<Product>>
}
