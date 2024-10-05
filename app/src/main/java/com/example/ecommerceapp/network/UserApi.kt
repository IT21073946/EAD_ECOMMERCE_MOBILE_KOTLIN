package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.User  // Import only your custom User model
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    // POST request for creating a new user
    @POST("api/User")
    fun createUser(@Body user: User): Call<User>
    @GET("api/User/pending")
    fun getPendingUsers(): Call<List<User>>

    // Approve user by ID (PUT request to set IsActive to true)
    @PUT("api/User/approve/{id}")
    fun approveUser(@Path("id") userId: String): Call<Void>

    @POST("api/Auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

}
