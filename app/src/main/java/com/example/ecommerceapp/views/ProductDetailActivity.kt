package com.example.ecommerceapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityProductDetailBinding
import com.example.ecommerceapp.models.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve product passed from the previous activity
        val product = intent.getSerializableExtra("product") as Product

        // Set product details in the UI
        binding.productName.text = product.productName
        binding.productDescription.text = product.description
        binding.productPrice.text = "Price: $${product.price}"
        Picasso.get().load(product.imageUrl).into(binding.productImage)
    }
}
