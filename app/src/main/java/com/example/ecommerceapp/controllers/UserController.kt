package com.example.ecommerceapp.controllers

import android.content.Context
import android.util.Log
import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.User
//import com.example.ecommerceapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController(private val context: Context) {

    // Function to create a user
    fun registerUser(user: User, callback: (Boolean, String?) -> Unit) {
        val call = ApiClient.userApi.createUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(true, "User registered successfully")
                } else {
                    callback(false, "Registration failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(false, "Error: ${t.message}")
            }
        })
    }

    fun getPendingUsers(callback: (List<User>?) -> Unit) {
        val call = ApiClient.userApi.getPendingUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("API Error", "Response Code: ${response.code()}, Message: ${response.message()}")
                    Log.e("API Error", "Response Body: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(null)
            }
        })
    }

    // Function to approve a user
    fun approveUser(user: User, callback: (Boolean) -> Unit) {
        val call = user.id?.let { ApiClient.userApi.approveUser(it) }
        if (call != null) {
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    callback(response.isSuccessful)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    callback(false)
                }
            })
        }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val loginRequest = LoginRequest(email = email, password = password)
        val call = ApiClient.userApi.loginUser(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Save the token using SharedPreferences or another mechanism
                    val token = response.body()?.token
                    if (token != null) {
                        saveToken(token)
                        callback(true, "Login successful!")
                    } else {
                        callback(false, "Failed to retrieve token")
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Invalid credentials"
                    callback(false, errorMessage)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(false, "Login failed: ${t.message}")
            }
        })
    }


    // Function to save the JWT token locally
    private fun saveToken(token: String) {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    // Function to retrieve the JWT token
    fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)
    }
}
