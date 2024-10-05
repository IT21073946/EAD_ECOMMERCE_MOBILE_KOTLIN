package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.databinding.ActivityProductDetailBinding
import com.example.ecommerceapp.models.Cart
import com.example.ecommerceapp.models.Product
import com.squareup.picasso.Picasso

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var cart: Cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding and Cart
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cart = Cart.getInstance()  // Get the singleton cart instance

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Retrieve product passed from the previous activity
        val product = intent.getSerializableExtra("product") as Product

        // Set product details in the UI
        binding.productName.text = product.productName
        binding.productDescription.text = product.description
        binding.productPrice.text = "Price: $${product.price}"
        Picasso.get().load(product.imageUrl).into(binding.productImage)
        binding.venderId.text = product.vendorId

        // Handle Add To Cart button click
        binding.addToCartButton.setOnClickListener {
            cart.addProduct(product)
            Toast.makeText(this, "${product.productName} added to cart", Toast.LENGTH_SHORT).show()
        }

        // Handle Cart icon click to open CartActivity
        binding.cartIcon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Handle Rate button click to open VendorRatingActivity
        binding.rateButton.setOnClickListener {
            val intent = Intent(this, VendorRatingActivity::class.java)
            intent.putExtra("vendorId", product.vendorId)  // Pass vendor ID to rating page
            startActivity(intent)
        }
    }

    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Go back to the previous activity (Product List)
        return true
    }
}
