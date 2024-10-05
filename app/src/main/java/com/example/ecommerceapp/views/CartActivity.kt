package com.example.ecommerceapp.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityCartBinding
import com.example.ecommerceapp.models.Cart

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        // Initialize ViewBinding
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get the cart instance and convert the list to a mutable list
        val cart = Cart.getInstance()
        cartAdapter = CartAdapter(cart.getProducts().toMutableList())  // Convert to MutableList

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = cartAdapter
    }

    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()  // Go back to the previous activity
        return true
    }
}
