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

    // GET request to retrieve pending users
    @GET("api/User/pending")
    fun getPendingUsers(): Call<List<User>>

    // Approve user by ID (PUT request to set IsActive to true)
    @PUT("api/User/approve/{id}")
    fun approveUser(@Path("id") userId: String): Call<Void>

    // POST request to login the user
    @POST("api/Auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // PUT request to update user profile by ID (for updating profile picture, contact number, etc.)
    @PUT("api/User/{id}")
    fun updateUser(@Path("id") userId: String, @Body user: User): Call<User>

    // PUT request to deactivate the user account (set IsActive to false)
    @PUT("api/User/deactivate/{id}")
    fun deactivateUser(@Path("id") userId: String): Call<Void>

    @GET("api/User/{id}")
    fun getUserById(@Path("id") userId: String): Call<User>

    // Get user by email (not ID)
    @GET("api/User/email/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

    @PUT("api/User/updateProfile/{id}")
    fun updateUserProfile(
        @Path("id") userId: String,
        @Body updatedUser: User
    ): Call<Void>

}
