package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.models.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    // LiveData to observe the list of products
    val products: MutableLiveData<List<Product>> = MutableLiveData()

    // Function to fetch products from the API
    fun getProducts() {
        // Get ProductApi instance from ApiClient
        val productApi = ApiClient.productApi
        val call = productApi.getProducts()

        // Make the network call
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    // Update the LiveData with the fetched products
                    products.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                // Handle API request failure
            }
        })
    }
}